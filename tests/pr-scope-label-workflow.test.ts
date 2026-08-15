import { readFileSync } from "node:fs";

import { describe, expect, it } from "vitest";

const workflow = readFileSync(".github/workflows/label-pr-scope.yml", "utf8").replaceAll("\r\n", "\n");

describe("pull request scope label workflow", () => {
  it("runs for new, reopened, and synchronized pull requests targeting master", () => {
    expect(workflow).toContain("pull_request_target:");
    expect(workflow).toContain("branches:\n      - master");
    expect(workflow).toContain("types:\n      - opened\n      - reopened\n      - synchronize");
    expect(workflow).not.toContain("pull_request:\n");
    expect(workflow).not.toContain("labeled\n");
    expect(workflow).not.toContain("unlabeled\n");
  });

  it("checks out and runs only trusted default-branch code", () => {
    expect(workflow).toContain("name: Checkout trusted default branch");
    expect(workflow).toContain("ref: ${{ github.event.repository.default_branch }}");
    expect(workflow).toContain("persist-credentials: false");
    expect(workflow).toContain("run: node scripts/label-pr-scope.mjs");
    expect(workflow).not.toContain("github.event.pull_request.head");
    expect(workflow).not.toContain("github.head_ref");
  });

  it("uses only the permissions required to read files and manage labels", () => {
    expect(workflow).toContain(
      "permissions:\n"
        + "  contents: read\n"
        + "  issues: write\n"
        + "  pull-requests: read",
    );
    expect(workflow).not.toContain("contents: write");
    expect(workflow).not.toContain("pull-requests: write");
    expect(workflow).not.toContain("write-all");
  });

  it("passes event metadata through environment variables and serializes runs per PR", () => {
    expect(workflow).toContain("group: label-pr-scope-${{ github.event.pull_request.number }}");
    expect(workflow).toContain("cancel-in-progress: true");
    expect(workflow).toContain("GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}");
    expect(workflow).toContain("PR_LABEL_PULL_NUMBER: ${{ github.event.pull_request.number }}");
    expect(workflow).toContain("PR_LABEL_CHANGED_FILES: ${{ github.event.pull_request.changed_files }}");
  });
});
