import catalogData from "@/data/problem-catalog.json";
import progressData from "@/data/progress.json";
import type { DynamicCatalogProblem, ProgressData } from "@/lib/types";

export type CatalogProvider = "leetcode" | "programmers" | "swea";

export type CatalogProblem = {
  provider: CatalogProvider;
  problemId: string;
  problemKey: string;
  title: string;
  difficulty: string;
  sourceUrl: string;
  slug?: string;
};

export type CatalogListItem = {
  problemKey: string;
  order: number;
  section: string;
  submissionKey: string;
};

export type CatalogList = {
  key: string;
  title: string;
  url: string;
  summary: string[];
  problems: CatalogProblem[];
  items: CatalogListItem[];
};

export type ProblemCatalog = {
  generatedAt: string;
  sources: string[];
  lists: CatalogList[];
  problems: CatalogProblem[];
};

export function mergeDynamicProblems(
  baseCatalog: ProblemCatalog,
  dynamicProblems: DynamicCatalogProblem[] = [],
): ProblemCatalog {
  const knownProblemKeys = new Set(baseCatalog.problems.map((problem) => problem.problemKey));
  const additions = dynamicProblems
    .filter((problem) => problem.provider === "swea" && !knownProblemKeys.has(problem.problemKey))
    .map((problem) => ({ ...problem } as CatalogProblem));
  if (additions.length === 0) return baseCatalog;

  const sweaList = baseCatalog.lists.find((list) => list.key === "swea");
  if (!sweaList) return baseCatalog;
  const problems = [...sweaList.problems, ...additions]
    .sort((left, right) => Number(left.problemId) - Number(right.problemId));
  const items = problems.map((problem, index) => ({
    problemKey: problem.problemKey,
    order: index + 1,
    section: problem.difficulty,
    submissionKey: problem.problemId,
  }));

  return {
    ...baseCatalog,
    lists: baseCatalog.lists.map((list) => list.key === "swea" ? { ...list, problems, items } : list),
    problems: [...baseCatalog.problems, ...additions],
  };
}

const progress = progressData as ProgressData;
export const catalog = mergeDynamicProblems(catalogData as ProblemCatalog, progress.dynamicProblems);

export const providerListKeys = new Set(["leetcode", "programmers", "swea"]);
export const catalogLists = catalog.lists.filter((list) => !providerListKeys.has(list.key));
export const providerLists = catalog.lists.filter((list) => providerListKeys.has(list.key));

export function isProviderList(list: CatalogList) {
  return providerListKeys.has(list.key);
}

export const problemByKey = new Map(catalog.problems.map((problem) => [problem.problemKey, problem]));
export const listByKey = new Map(catalog.lists.map((list) => [list.key, list]));
export const catalogProblemKeys = new Set(catalog.problems.map((problem) => problem.problemKey));

export function getProblem(problemKey: string) {
  const problem = problemByKey.get(problemKey);
  if (!problem) {
    throw new Error(`Unknown problem key: ${problemKey}`);
  }
  return problem;
}

export function getList(key: string) {
  const list = listByKey.get(key);
  if (!list) {
    throw new Error(`Unknown problem list: ${key}`);
  }
  return list;
}

export function getListProblems(list: CatalogList) {
  return list.items.map((item) => ({
    ...item,
    problem: getProblem(item.problemKey),
  }));
}

export function getProblemSourceUrl(problemKey: string) {
  return getProblem(problemKey).sourceUrl;
}
