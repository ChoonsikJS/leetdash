import path from "node:path";
import { fileURLToPath } from "node:url";

import { GitHubReviewClient } from "./opencode-review-clients.mjs";
import {
  hasCompletePullRequestFileList,
  inspectSubmissionChanges,
  validateMetaSource,
} from "./validate-submission-pr.mjs";

const validationMarker = "<!-- leetdash-submission-validation -->";
const failureMessage = "Submission validation reporting failed.";
const shaPattern = /^[0-9a-f]{40}$/;
const repositoryPattern = /^[A-Za-z0-9_.-]+\/[A-Za-z0-9_.-]+$/;
const maxRenderedErrors = 20;
const maxRenderedErrorLength = 500;

function parseArgs(argv) {
  const args = {};
  for (let index = 0; index < argv.length; index += 1) {
    const argument = argv[index];
    if (["--base", "--head", "--pull-number"].includes(argument)) {
      args[argument.slice(2).replace(/-([a-z])/g, (_, letter) => letter.toUpperCase())] = argv[index + 1];
      index += 1;
    }
  }
  return args;
}

function validRepository(value) {
  return repositoryPattern.test(value ?? "")
    && value.split("/").every((segment) => segment !== "." && segment !== "..");
}

function validateConfiguration(args, env) {
  return validRepository(env.GITHUB_REPOSITORY)
    && typeof env.GITHUB_TOKEN === "string"
    && env.GITHUB_TOKEN.length > 0
    && shaPattern.test(args.base ?? "")
    && shaPattern.test(args.head ?? "")
    && /^[1-9]\d*$/.test(args.pullNumber ?? "");
}

function normalizePullRequestFile(file) {
  if (!file || typeof file.status !== "string" || typeof file.filename !== "string") {
    throw new Error(failureMessage);
  }
  const statuses = { added: "A", modified: "M", removed: "D", renamed: "R" };
  return { status: statuses[file.status] ?? file.status, path: file.filename };
}

function sanitizeError(error) {
  return String(error ?? "")
    .replace(/[\u0000-\u001f\u007f]/g, " ")
    .replace(/`/g, "'")
    .replace(/\s+/g, " ")
    .trim()
    .slice(0, maxRenderedErrorLength);
}

function renderValidationComment({ errors, headSha, runUrl }) {
  const visibleErrors = errors.slice(0, maxRenderedErrors).map((error) => `- \`${sanitizeError(error)}\``);
  const omittedCount = errors.length - visibleErrors.length;
  if (omittedCount > 0) visibleErrors.push(`- 그 외 ${omittedCount}개 오류는 Actions 로그를 확인해 주세요.`);
  const runLine = runUrl ? `- 실행 로그: [GitHub Actions](${runUrl})` : "";

  return `${validationMarker}
## 제출 PR 검증 실패

제출 파일 구조를 확인하지 못해 자동 검증이 중단됐습니다.

### 실패 이유

${visibleErrors.join("\n")}

### 조치 방법

1. 파일을 \`submissions/<본인 GitHub 사용자명>/<sourceKey>/<submissionKey>/\` 아래에 두세요.
2. 파일명은 \`Solution.<지원 확장자>\`, \`README.md\`, \`meta.json\` 중 하나를 사용하세요. 지원 확장자는 \`c, cc, cpp, cs, dart, go, java, js, kt, php, py, rb, rs, scala, sql, swift, ts\`입니다.
3. \`sourceKey/submissionKey\`가 \`data/problem-catalog.json\`에 있고 제출 경로가 PR 작성자 계정과 일치하는지 확인하세요.
4. 수정 커밋을 push하면 검증이 다시 실행되고 이 댓글도 갱신됩니다.

- 대상 커밋: \`${headSha}\`
${runLine}

_이 댓글은 제출 검증 Action이 자동으로 관리합니다._`;
}

function managedValidationComments(comments) {
  if (!Array.isArray(comments)) throw new Error(failureMessage);
  return comments.filter((comment) => (
    comment?.user?.login === "github-actions[bot]"
    && Number.isSafeInteger(comment.id)
    && typeof comment.body === "string"
    && comment.body.includes(validationMarker)
  ));
}

async function loadValidationErrors({ githubClient, pullNumber, baseSha, headSha, catalog, users }) {
  const pullRequest = await githubClient.getPullRequest(pullNumber);
  if (
    pullRequest?.number !== pullNumber
    || pullRequest?.base?.sha !== baseSha
    || pullRequest?.head?.sha !== headSha
    || !validRepository(pullRequest?.head?.repo?.full_name)
    || typeof pullRequest?.user?.login !== "string"
  ) {
    throw new Error(failureMessage);
  }
  const files = await githubClient.listPullRequestFiles(pullNumber);
  if (!hasCompletePullRequestFileList(pullRequest, files)) throw new Error(failureMessage);
  const changedFiles = files.map(normalizePullRequestFile);
  const inspection = inspectSubmissionChanges(changedFiles, {
    authorLogin: pullRequest.user.login,
    catalogInput: catalog,
    checkFileExists: false,
    usersInput: users,
  });
  for (const file of inspection.submissionFiles) {
    const filename = file.path.slice(file.path.lastIndexOf("/") + 1).toLowerCase();
    if (filename !== "meta.json" || file.status === "D" || file.status === "removed") continue;
    const source = await githubClient.getFileContent({
      path: file.path,
      ref: headSha,
      repository: pullRequest.head.repo.full_name,
    });
    validateMetaSource(file.path, source, inspection.errors);
  }
  return inspection.errors;
}

async function syncValidationComment({ githubClient, pullNumber, errors, headSha, runUrl }) {
  const comments = managedValidationComments(await githubClient.listIssueComments(pullNumber));
  if (errors.length === 0) {
    for (const comment of comments) await githubClient.deleteReviewComment(comment.id);
    return { action: comments.length > 0 ? "deleted" : "none" };
  }

  await githubClient.upsertReviewComment({
    pullNumber,
    commentId: comments[0]?.id,
    body: renderValidationComment({ errors, headSha, runUrl }),
  });
  for (const duplicate of comments.slice(1)) await githubClient.deleteReviewComment(duplicate.id);
  return { action: comments.length > 0 ? "updated" : "created" };
}

async function main({
  argv = process.argv.slice(2),
  env = process.env,
  githubClient,
  catalog,
  users,
  stderr = console.error,
} = {}) {
  try {
    const args = parseArgs(argv);
    if (!validateConfiguration(args, env)) throw new Error(failureMessage);
    const client = githubClient ?? new GitHubReviewClient({
      repository: env.GITHUB_REPOSITORY,
      token: env.GITHUB_TOKEN,
    });
    const pullNumber = Number(args.pullNumber);
    const errors = await loadValidationErrors({
      githubClient: client,
      pullNumber,
      baseSha: args.base,
      headSha: args.head,
      catalog,
      users,
    });
    const runUrl = env.GITHUB_SERVER_URL && env.GITHUB_RUN_ID
      ? `${env.GITHUB_SERVER_URL.replace(/\/$/, "")}/${env.GITHUB_REPOSITORY}/actions/runs/${env.GITHUB_RUN_ID}`
      : undefined;
    const result = await syncValidationComment({
      githubClient: client,
      pullNumber,
      errors,
      headSha: args.head,
      runUrl,
    });
    return { exitCode: 0, errors, ...result };
  } catch {
    stderr(failureMessage);
    return { exitCode: 1 };
  }
}

const invokedPath = process.argv[1] ? path.resolve(process.argv[1]) : "";
if (invokedPath === fileURLToPath(import.meta.url)) {
  main().then(({ exitCode }) => { process.exitCode = exitCode; });
}

export {
  loadValidationErrors,
  main,
  managedValidationComments,
  renderValidationComment,
  syncValidationComment,
  validationMarker,
};
