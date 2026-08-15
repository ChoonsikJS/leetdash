import { catalog, type CatalogProblem, type CatalogProvider } from "@/lib/catalog";

export type ProblemSearchItem = Pick<
  CatalogProblem,
  "provider" | "problemId" | "title" | "difficulty" | "sourceUrl"
>;

export type ProblemSearchGroup = {
  provider: CatalogProvider;
  items: ProblemSearchItem[];
};

export const problemSearchProviders: CatalogProvider[] = ["leetcode", "programmers", "swea"];

export const problemSearchProviderLabels: Record<CatalogProvider, string> = {
  leetcode: "LeetCode",
  programmers: "Programmers",
  swea: "SWEA",
};

function normalizeSearchText(value: string) {
  return value.normalize("NFKC").trim().toLocaleLowerCase();
}

export function getProblemSearchIndex(): ProblemSearchItem[] {
  return catalog.problems.map(({ provider, problemId, title, difficulty, sourceUrl }) => ({
    provider,
    problemId,
    title,
    difficulty,
    sourceUrl,
  }));
}

export function searchProblems(items: ProblemSearchItem[], rawQuery: string): ProblemSearchGroup[] {
  const query = normalizeSearchText(rawQuery);
  if (!query) return [];

  const matches = new Map<CatalogProvider, ProblemSearchItem[]>(
    problemSearchProviders.map((provider) => [provider, []]),
  );

  for (const item of items) {
    if (
      normalizeSearchText(item.problemId).includes(query)
      || normalizeSearchText(item.title).includes(query)
    ) {
      matches.get(item.provider)?.push(item);
    }
  }

  return problemSearchProviders.flatMap((provider) => {
    const providerItems = matches.get(provider) ?? [];
    return providerItems.length > 0 ? [{ provider, items: providerItems }] : [];
  });
}
