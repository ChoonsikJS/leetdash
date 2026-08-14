import { describe, expect, it, vi } from "vitest";

import {
  loadValidationErrors,
  main,
  renderValidationComment,
  syncValidationComment,
  validationMarker,
} from "../scripts/report-submission-validation.mjs";

const sha = (character) => character.repeat(40);
const validPath = "submissions/ada/top-interview-easy/1/Solution.java";
const invalidPath = "submissions/ada/top-interview-easy/1/answer.java";
const catalog = {
  lists: [{ key: "top-interview-easy", items: [{ submissionKey: "1" }] }],
};
const users = {
  users: [{ id: "ada", displayName: "Ada Lovelace", githubUsername: "ada" }],
};

function pullRequest(files, overrides = {}) {
  return {
    number: 42,
    changed_files: files.length,
    user: { login: "ada" },
    base: { sha: sha("b") },
    head: { sha: sha("c"), repo: { full_name: "fork-user/leetdash" } },
    ...overrides,
  };
}

describe("loadValidationErrors", () => {
  it("returns actionable validation errors for mixed pull requests", async () => {
    const files = [
      { status: "modified", filename: "app/page.tsx" },
      { status: "added", filename: invalidPath },
    ];
    const githubClient = {
      getPullRequest: async () => pullRequest(files),
      listPullRequestFiles: async () => files,
    };

    await expect(loadValidationErrors({
      githubClient,
      pullNumber: 42,
      baseSha: sha("b"),
      headSha: sha("c"),
      catalog,
      users,
    })).resolves.toEqual([
      `${invalidPath}: file must be solution.<supported ext>, README.md, or meta.json.`,
    ]);
  });

  it("fails closed when the pull-request head no longer matches", async () => {
    const files = [{ status: "added", filename: validPath }];
    await expect(loadValidationErrors({
      githubClient: {
        getPullRequest: async () => pullRequest(files, { head: { sha: sha("d"), repo: { full_name: "fork-user/leetdash" } } }),
        listPullRequestFiles: async () => { throw new Error("must not list files"); },
      },
      pullNumber: 42,
      baseSha: sha("b"),
      headSha: sha("c"),
      catalog,
      users,
    })).rejects.toThrow("Submission validation reporting failed.");
  });

  it("reports invalid meta.json content loaded from the exact head", async () => {
    const metaPath = "submissions/ada/top-interview-easy/1/meta.json";
    const files = [{ status: "modified", filename: metaPath }];
    const getFileContent = vi.fn(async () => "{ invalid json");

    await expect(loadValidationErrors({
      githubClient: {
        getPullRequest: async () => pullRequest(files),
        listPullRequestFiles: async () => files,
        getFileContent,
      },
      pullNumber: 42,
      baseSha: sha("b"),
      headSha: sha("c"),
      catalog,
      users,
    })).resolves.toEqual([`${metaPath}: meta.json must be valid JSON.`]);

    expect(getFileContent).toHaveBeenCalledWith({
      path: metaPath,
      ref: sha("c"),
      repository: "fork-user/leetdash",
    });
  });
});

describe("validation failure comment", () => {
  it("includes sanitized reasons and concrete remediation", () => {
    const body = renderValidationComment({
      errors: [`${invalidPath}\n\`unsafe\`: invalid filename`],
      headSha: sha("c"),
      runUrl: "https://github.example/example/leetdash/actions/runs/9",
    });

    expect(body).toContain(validationMarker);
    expect(body).toContain("제출 PR 검증 실패");
    expect(body).toContain("Solution.<지원 확장자>");
    expect(body).toContain("data/problem-catalog.json");
    expect(body).toContain("'unsafe': invalid filename");
    expect(body).not.toContain("\n`unsafe`");
  });

  it("updates one bot comment, removes duplicates, and ignores spoofed markers", async () => {
    const mutations = [];
    const githubClient = {
      listIssueComments: async () => [
        { id: 10, user: { login: "ada" }, body: validationMarker },
        { id: 11, user: { login: "github-actions[bot]" }, body: validationMarker },
        { id: 12, user: { login: "github-actions[bot]" }, body: validationMarker },
      ],
      upsertReviewComment: async (value) => { mutations.push(["upsert", value]); },
      deleteReviewComment: async (id) => { mutations.push(["delete", id]); },
    };

    await expect(syncValidationComment({
      githubClient,
      pullNumber: 42,
      errors: ["invalid filename"],
      headSha: sha("c"),
    })).resolves.toEqual({ action: "updated" });

    expect(mutations[0][0]).toBe("upsert");
    expect(mutations[0][1]).toMatchObject({ pullNumber: 42, commentId: 11 });
    expect(mutations[1]).toEqual(["delete", 12]);
    expect(mutations.flat()).not.toContain(10);
  });

  it("deletes stale managed comments after validation succeeds", async () => {
    const deleteReviewComment = vi.fn(async () => {});
    await expect(syncValidationComment({
      githubClient: {
        listIssueComments: async () => [
          { id: 11, user: { login: "github-actions[bot]" }, body: validationMarker },
        ],
        deleteReviewComment,
      },
      pullNumber: 42,
      errors: [],
      headSha: sha("c"),
    })).resolves.toEqual({ action: "deleted" });

    expect(deleteReviewComment).toHaveBeenCalledWith(11);
  });
});

describe("reporter CLI", () => {
  it("posts a validation comment for the exact pull-request revision", async () => {
    const files = [{ status: "added", filename: invalidPath }];
    const comments = [];
    const githubClient = {
      getPullRequest: async () => pullRequest(files),
      listPullRequestFiles: async () => files,
      listIssueComments: async () => [],
      upsertReviewComment: async (value) => { comments.push(value); },
      deleteReviewComment: async () => {},
    };

    const outcome = await main({
      argv: ["--base", sha("b"), "--head", sha("c"), "--pull-number", "42"],
      env: {
        GITHUB_REPOSITORY: "example/leetdash",
        GITHUB_TOKEN: "github-secret",
        GITHUB_SERVER_URL: "https://github.example",
        GITHUB_RUN_ID: "9",
      },
      githubClient,
      catalog,
      users,
    });

    expect(outcome).toMatchObject({ exitCode: 0, action: "created" });
    expect(comments).toHaveLength(1);
    expect(comments[0].body).toContain(invalidPath);
    expect(comments[0].body).toContain("https://github.example/example/leetdash/actions/runs/9");
  });

  it("sanitizes infrastructure failures", async () => {
    const stderr = vi.fn();
    const outcome = await main({
      argv: ["--base", sha("b"), "--head", sha("c"), "--pull-number", "42"],
      env: { GITHUB_REPOSITORY: "example/leetdash", GITHUB_TOKEN: "github-secret" },
      githubClient: { getPullRequest: async () => { throw new Error("provider-secret"); } },
      catalog,
      users,
      stderr,
    });

    expect(outcome).toEqual({ exitCode: 1 });
    expect(stderr).toHaveBeenCalledWith("Submission validation reporting failed.");
    expect(stderr.mock.calls.flat().join(" ")).not.toContain("provider-secret");
  });
});
