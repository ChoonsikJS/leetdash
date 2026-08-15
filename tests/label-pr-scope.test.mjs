import { describe, expect, it, vi } from "vitest";

import {
  GitHubPullRequestLabelClient,
  classifyPullRequestFiles,
  failureMessage,
  main,
  planLabelChanges,
  syncPullRequestLabels,
} from "../scripts/label-pr-scope.mjs";

const submissionPath = "submissions/ada/top-interview-easy/1/Solution.java";

function response(body, status = 200) {
  return new Response(status === 204 ? null : JSON.stringify(body), {
    status,
    headers: { "content-type": "application/json" },
  });
}

function validEnv(overrides = {}) {
  return {
    GITHUB_REPOSITORY: "example/leetdash",
    GITHUB_TOKEN: "github-secret-token",
    PR_LABEL_PULL_NUMBER: "42",
    PR_LABEL_CHANGED_FILES: "1",
    ...overrides,
  };
}

describe("pull request scope classification", () => {
  it("labels participant submission changes as submission-only", () => {
    expect(classifyPullRequestFiles([{ filename: submissionPath }])).toEqual(["submission"]);
  });

  it("labels application changes as feature-only", () => {
    expect(classifyPullRequestFiles([{ filename: "app/page.tsx" }])).toEqual(["feature"]);
  });

  it("applies both labels to mixed changes", () => {
    expect(classifyPullRequestFiles([
      { filename: submissionPath },
      { filename: "scripts/build-progress.mjs" },
    ])).toEqual(["feature", "submission"]);
  });

  it("uses the existing validator boundary for repository and malformed submission paths", () => {
    expect(classifyPullRequestFiles([{ filename: "submissions/README.md" }])).toEqual(["feature"]);
    expect(classifyPullRequestFiles([{ filename: "submissions/ada/Solution.java" }])).toEqual(["feature"]);
    expect(classifyPullRequestFiles([{ filename: "submissions/ada/list/1/meta.json" }])).toEqual(["submission"]);
  });

  it("treats deleted and renamed destination filenames according to their API path", () => {
    expect(classifyPullRequestFiles([
      { filename: submissionPath, status: "removed" },
    ])).toEqual(["submission"]);
    expect(classifyPullRequestFiles([
      { filename: "app/moved-solution.java", previous_filename: submissionPath, status: "renamed" },
    ])).toEqual(["feature"]);
  });
});

describe("managed label reconciliation", () => {
  it("adds missing labels and removes stale managed labels without touching unrelated labels", () => {
    expect(planLabelChanges(["submission", "bug", "priority"], ["feature"])).toEqual({
      add: ["feature"],
      remove: ["submission"],
    });
  });

  it("does no work when managed labels already match", () => {
    expect(planLabelChanges(["feature", "submission", "bug"], ["feature", "submission"])).toEqual({
      add: [],
      remove: [],
    });
  });

  it("synchronizes removals before additions and preserves labels outside its ownership", async () => {
    const mutations = [];
    const client = {
      listPullRequestFiles: vi.fn(async () => [{ filename: "app/page.tsx" }]),
      listIssueLabels: vi.fn(async () => ["submission", "bug"]),
      removeLabel: vi.fn(async (_pullNumber, label) => { mutations.push(["remove", label]); }),
      addLabels: vi.fn(async (_pullNumber, labels) => { mutations.push(["add", labels]); }),
    };

    await expect(syncPullRequestLabels({
      client,
      pullNumber: 42,
      expectedFileCount: 1,
    })).resolves.toEqual({ labels: ["feature"], add: ["feature"], remove: ["submission"] });

    expect(mutations).toEqual([
      ["remove", "submission"],
      ["add", ["feature"]],
    ]);
    expect(mutations.flat(2)).not.toContain("bug");
  });

  it("avoids write calls when labels are already synchronized", async () => {
    const client = {
      listPullRequestFiles: vi.fn(async () => [{ filename: submissionPath }]),
      listIssueLabels: vi.fn(async () => ["submission", "help wanted"]),
      removeLabel: vi.fn(),
      addLabels: vi.fn(),
    };

    await syncPullRequestLabels({ client, pullNumber: 42, expectedFileCount: 1 });

    expect(client.removeLabel).not.toHaveBeenCalled();
    expect(client.addLabels).not.toHaveBeenCalled();
  });
});

describe("GitHub label client", () => {
  it("paginates pull request files and verifies the exact expected count", async () => {
    const firstPage = Array.from({ length: 100 }, (_, index) => ({ filename: `app/file-${index}.ts` }));
    const fetchImpl = vi.fn(async (url, init) => {
      const requestUrl = new URL(url);
      expect(init.headers.Authorization).toBe("Bearer github-secret-token");
      return requestUrl.searchParams.get("page") === "1"
        ? response(firstPage)
        : response([{ filename: submissionPath }]);
    });
    const client = new GitHubPullRequestLabelClient({
      repository: "example/leetdash",
      token: "github-secret-token",
      fetchImpl,
    });

    await expect(client.listPullRequestFiles(42, 101)).resolves.toHaveLength(101);
    expect(fetchImpl).toHaveBeenCalledTimes(2);
    expect(String(fetchImpl.mock.calls[0][0])).toContain("/pulls/42/files?per_page=100&page=1");
    expect(String(fetchImpl.mock.calls[1][0])).toContain("/pulls/42/files?per_page=100&page=2");
  });

  it("fails closed on incomplete lists before any label mutation", async () => {
    const fetchImpl = vi.fn(async (url) => {
      expect(String(url)).toContain("/pulls/42/files");
      return response([{ filename: "app/page.tsx" }]);
    });
    const client = new GitHubPullRequestLabelClient({
      repository: "example/leetdash",
      token: "github-secret-token",
      fetchImpl,
    });

    await expect(syncPullRequestLabels({
      client,
      pullNumber: 42,
      expectedFileCount: 2,
    })).rejects.toThrow(failureMessage);
    expect(fetchImpl).toHaveBeenCalledTimes(1);
  });

  it("rejects the GitHub 3,000-file API ceiling without making a request", async () => {
    const fetchImpl = vi.fn();
    const client = new GitHubPullRequestLabelClient({
      repository: "example/leetdash",
      token: "github-secret-token",
      fetchImpl,
    });

    await expect(client.listPullRequestFiles(42, 3001)).rejects.toThrow(failureMessage);
    expect(fetchImpl).not.toHaveBeenCalled();
  });

  it("preserves unrelated labels while using the issue-label mutation endpoints", async () => {
    const requests = [];
    const fetchImpl = vi.fn(async (url, init) => {
      requests.push({ url: String(url), method: init.method, body: init.body });
      if (String(url).includes("/pulls/")) return response([{ filename: "app/page.tsx" }]);
      if (init.method === "GET") return response([{ name: "submission" }, { name: "bug" }]);
      if (init.method === "DELETE") return response(undefined, 204);
      return response([{ name: "bug" }, { name: "feature" }]);
    });
    const client = new GitHubPullRequestLabelClient({
      repository: "example/leetdash",
      token: "github-secret-token",
      fetchImpl,
    });

    await syncPullRequestLabels({ client, pullNumber: 42, expectedFileCount: 1 });

    expect(requests).toEqual(expect.arrayContaining([
      expect.objectContaining({ url: expect.stringContaining("/issues/42/labels/submission"), method: "DELETE" }),
      expect.objectContaining({ url: expect.stringContaining("/issues/42/labels"), method: "POST", body: JSON.stringify({ labels: ["feature"] }) }),
    ]));
    expect(requests.some((request) => request.url.endsWith("/labels/bug"))).toBe(false);
  });
});

describe("label synchronization CLI", () => {
  it("reports a sanitized failure without exposing token or provider response", async () => {
    const stderr = vi.fn();
    const fetchImpl = vi.fn(async () => response({ message: "provider-secret-body" }, 500));

    const result = await main({ env: validEnv(), fetchImpl, stderr });

    expect(result).toEqual({ exitCode: 1 });
    expect(stderr).toHaveBeenCalledWith(failureMessage);
    expect(JSON.stringify(stderr.mock.calls)).not.toContain("github-secret-token");
    expect(JSON.stringify(stderr.mock.calls)).not.toContain("provider-secret-body");
  });

  it("rejects malformed configuration before constructing a client", async () => {
    const fetchImpl = vi.fn();
    const stderr = vi.fn();

    await expect(main({
      env: validEnv({ GITHUB_REPOSITORY: "../unsafe", PR_LABEL_PULL_NUMBER: "0" }),
      fetchImpl,
      stderr,
    })).resolves.toEqual({ exitCode: 1 });

    expect(fetchImpl).not.toHaveBeenCalled();
    expect(stderr).toHaveBeenCalledWith(failureMessage);
  });

  it("rejects an event above the file API ceiling before constructing a client", async () => {
    const fetchImpl = vi.fn();

    await expect(main({
      env: validEnv({ PR_LABEL_CHANGED_FILES: "3001" }),
      fetchImpl,
      stderr: vi.fn(),
    })).resolves.toEqual({ exitCode: 1 });

    expect(fetchImpl).not.toHaveBeenCalled();
  });
});
