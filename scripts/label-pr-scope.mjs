import path from "node:path";
import { fileURLToPath } from "node:url";

import { isParticipantSubmissionPath } from "./validate-submission-pr.mjs";

const failureMessage = "Pull request label synchronization failed.";
const managedLabels = ["feature", "submission"];
const maxPullRequestFiles = 3000;
const repositoryPattern = /^[A-Za-z0-9_.-]+\/[A-Za-z0-9_.-]+$/;

class PullRequestLabelFailure extends Error {
  constructor(detail = "") {
    super(detail ? `${failureMessage} ${detail}` : failureMessage);
    this.name = "PullRequestLabelFailure";
  }
}

function safeRequestId(response) {
  const requestId = response?.headers?.get?.("x-github-request-id") ?? "";
  return /^[A-Za-z0-9:-]{1,128}$/.test(requestId) ? requestId : "unavailable";
}

function requestDetail(method, apiPath, response, outcome) {
  const status = Number.isInteger(response?.status) ? response.status : "unavailable";
  return `GitHub API ${method} ${apiPath} ${outcome}; status=${status}; request-id=${safeRequestId(response)}.`;
}

function validRepository(value) {
  return repositoryPattern.test(value ?? "")
    && value.split("/").every((segment) => segment !== "." && segment !== "..");
}

function validPositiveInteger(value) {
  return /^[1-9]\d*$/.test(String(value ?? ""));
}

function classifyPullRequestFiles(files) {
  if (!Array.isArray(files) || files.length === 0) throw new PullRequestLabelFailure();

  let hasFeature = false;
  let hasSubmission = false;
  for (const file of files) {
    if (!file || typeof file.filename !== "string" || file.filename.length === 0) {
      throw new PullRequestLabelFailure();
    }
    if (isParticipantSubmissionPath(file.filename)) hasSubmission = true;
    else hasFeature = true;
  }

  return managedLabels.filter((label) => (
    label === "feature" ? hasFeature : hasSubmission
  ));
}

function planLabelChanges(currentLabels, desiredLabels) {
  if (
    !Array.isArray(currentLabels)
    || !currentLabels.every((label) => typeof label === "string")
    || !Array.isArray(desiredLabels)
    || !desiredLabels.every((label) => managedLabels.includes(label))
  ) {
    throw new PullRequestLabelFailure();
  }

  const current = new Set(currentLabels);
  const desired = new Set(desiredLabels);
  return {
    add: managedLabels.filter((label) => desired.has(label) && !current.has(label)),
    remove: managedLabels.filter((label) => current.has(label) && !desired.has(label)),
  };
}

class GitHubPullRequestLabelClient {
  constructor({ repository, token, fetchImpl = fetch }) {
    if (!validRepository(repository) || typeof token !== "string" || token.length === 0 || typeof fetchImpl !== "function") {
      throw new PullRequestLabelFailure();
    }
    this.repository = repository;
    this.token = token;
    this.fetchImpl = fetchImpl;
  }

  async request(apiPath, { method = "GET", body } = {}) {
    let response;
    try {
      response = await this.fetchImpl(`https://api.github.com/repos/${this.repository}${apiPath}`, {
        method,
        headers: {
          Accept: "application/vnd.github+json",
          Authorization: `Bearer ${this.token}`,
          "X-GitHub-Api-Version": "2022-11-28",
          ...(body === undefined ? {} : { "Content-Type": "application/json" }),
        },
        ...(body === undefined ? {} : { body: JSON.stringify(body) }),
      });
    } catch {
      throw new PullRequestLabelFailure(
        requestDetail(method, apiPath, undefined, "failed before receiving a response"),
      );
    }
    if (!response?.ok) {
      throw new PullRequestLabelFailure(requestDetail(method, apiPath, response, "returned an error"));
    }
    if (response.status === 204) return undefined;
    try {
      return await response.json();
    } catch {
      throw new PullRequestLabelFailure(requestDetail(method, apiPath, response, "returned invalid JSON"));
    }
  }

  async listPullRequestFiles(pullNumber, expectedCount) {
    if (
      !Number.isSafeInteger(pullNumber)
      || pullNumber < 1
      || !Number.isSafeInteger(expectedCount)
      || expectedCount < 1
      || expectedCount > maxPullRequestFiles
    ) {
      throw new PullRequestLabelFailure();
    }

    const files = [];
    for (let page = 1; page <= Math.ceil(maxPullRequestFiles / 100); page += 1) {
      const pageFiles = await this.request(`/pulls/${pullNumber}/files?per_page=100&page=${page}`);
      if (!Array.isArray(pageFiles)) throw new PullRequestLabelFailure();
      files.push(...pageFiles);
      if (files.length >= expectedCount || pageFiles.length < 100) break;
    }
    if (files.length !== expectedCount) {
      throw new PullRequestLabelFailure(
        `GitHub API returned ${files.length} pull request files; expected ${expectedCount}.`,
      );
    }
    return files;
  }

  async listIssueLabels(pullNumber) {
    if (!Number.isSafeInteger(pullNumber) || pullNumber < 1) throw new PullRequestLabelFailure();

    const labels = [];
    for (let page = 1; page <= 100; page += 1) {
      const pageLabels = await this.request(`/issues/${pullNumber}/labels?per_page=100&page=${page}`);
      if (!Array.isArray(pageLabels)) throw new PullRequestLabelFailure();
      for (const label of pageLabels) {
        if (!label || typeof label.name !== "string") throw new PullRequestLabelFailure();
        labels.push(label.name);
      }
      if (pageLabels.length < 100) return labels;
    }
    throw new PullRequestLabelFailure();
  }

  async addLabels(pullNumber, labels) {
    if (
      !Number.isSafeInteger(pullNumber)
      || pullNumber < 1
      || !Array.isArray(labels)
      || !labels.every((label) => managedLabels.includes(label))
    ) {
      throw new PullRequestLabelFailure();
    }
    if (labels.length === 0) return;
    await this.request(`/issues/${pullNumber}/labels`, { method: "POST", body: { labels } });
  }

  async removeLabel(pullNumber, label) {
    if (!Number.isSafeInteger(pullNumber) || pullNumber < 1 || !managedLabels.includes(label)) {
      throw new PullRequestLabelFailure();
    }
    await this.request(`/issues/${pullNumber}/labels/${encodeURIComponent(label)}`, { method: "DELETE" });
  }
}

async function syncPullRequestLabels({ client, pullNumber, expectedFileCount }) {
  const files = await client.listPullRequestFiles(pullNumber, expectedFileCount);
  const desiredLabels = classifyPullRequestFiles(files);
  const currentLabels = await client.listIssueLabels(pullNumber);
  const changes = planLabelChanges(currentLabels, desiredLabels);

  for (const label of changes.remove) await client.removeLabel(pullNumber, label);
  if (changes.add.length > 0) await client.addLabels(pullNumber, changes.add);

  return { labels: desiredLabels, ...changes };
}

function configurationFromEnvironment(env) {
  if (
    !validRepository(env?.GITHUB_REPOSITORY)
    || typeof env?.GITHUB_TOKEN !== "string"
    || env.GITHUB_TOKEN.length === 0
    || !validPositiveInteger(env?.PR_LABEL_PULL_NUMBER)
    || !validPositiveInteger(env?.PR_LABEL_CHANGED_FILES)
  ) {
    throw new PullRequestLabelFailure();
  }

  const pullNumber = Number(env.PR_LABEL_PULL_NUMBER);
  const expectedFileCount = Number(env.PR_LABEL_CHANGED_FILES);
  if (
    !Number.isSafeInteger(pullNumber)
    || !Number.isSafeInteger(expectedFileCount)
    || expectedFileCount > maxPullRequestFiles
  ) {
    throw new PullRequestLabelFailure();
  }

  return {
    repository: env.GITHUB_REPOSITORY,
    token: env.GITHUB_TOKEN,
    pullNumber,
    expectedFileCount,
  };
}

async function main({
  env = process.env,
  fetchImpl = fetch,
  client,
  stdout = console.log,
  stderr = console.error,
} = {}) {
  try {
    const config = configurationFromEnvironment(env);
    const githubClient = client ?? new GitHubPullRequestLabelClient({
      repository: config.repository,
      token: config.token,
      fetchImpl,
    });
    const result = await syncPullRequestLabels({
      client: githubClient,
      pullNumber: config.pullNumber,
      expectedFileCount: config.expectedFileCount,
    });
    stdout(`Synchronized pull request labels: ${result.labels.join(", ")}`);
    return { exitCode: 0, ...result };
  } catch (error) {
    stderr(error instanceof PullRequestLabelFailure ? error.message : failureMessage);
    return { exitCode: 1 };
  }
}

const invokedPath = process.argv[1] ? path.resolve(process.argv[1]) : "";
if (invokedPath === fileURLToPath(import.meta.url)) {
  main().then(({ exitCode }) => { process.exitCode = exitCode; });
}

export {
  GitHubPullRequestLabelClient,
  PullRequestLabelFailure,
  classifyPullRequestFiles,
  failureMessage,
  main,
  managedLabels,
  planLabelChanges,
  syncPullRequestLabels,
};
