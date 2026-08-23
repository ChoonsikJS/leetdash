import { describe, expect, it } from "vitest";
import {
  getProblemSearchIndex,
  searchProblems,
  type ProblemSearchItem,
} from "@/lib/problem-search";

const items: ProblemSearchItem[] = [
  {
    provider: "swea",
    problemId: "312",
    title: "자료 구조 연습",
    difficulty: "D3",
    sourceUrl: "https://example.com/swea/312",
  },
  {
    provider: "leetcode",
    problemId: "12",
    title: "Integer to Roman",
    difficulty: "medium",
    sourceUrl: "https://example.com/leetcode/12",
  },
  {
    provider: "leetcode",
    problemId: "120",
    title: "Triangle",
    difficulty: "medium",
    sourceUrl: "https://example.com/leetcode/120",
  },
  {
    provider: "programmers",
    problemId: "1",
    title: "Two SUM Practice",
    difficulty: "level-1",
    sourceUrl: "https://example.com/programmers/1",
  },
  {
    provider: "leetcode",
    problemId: "1",
    title: "Two Sum",
    difficulty: "easy",
    sourceUrl: "https://example.com/leetcode/1",
  },
];

describe("problem search", () => {
  it("partially matches problem numbers across providers and groups in provider order", () => {
    const groups = searchProblems(items, "12");

    expect(groups.map((group) => group.provider)).toEqual(["leetcode", "swea"]);
    expect(groups[0]?.items.map((item) => item.problemId)).toEqual(["12", "120"]);
    expect(groups[1]?.items.map((item) => item.problemId)).toEqual(["312"]);
  });

  it("partially matches titles without case sensitivity and trims the query", () => {
    const groups = searchProblems(items, "  sum ");

    expect(groups).toHaveLength(2);
    expect(groups[0]?.items.map((item) => item.title)).toEqual(["Two Sum"]);
    expect(groups[1]?.items.map((item) => item.title)).toEqual(["Two SUM Practice"]);
  });

  it("returns no groups for an empty or unmatched query", () => {
    expect(searchProblems(items, "   ")).toEqual([]);
    expect(searchProblems(items, "not-found")).toEqual([]);
  });

  it("builds a lightweight index without catalog-only fields", () => {
    const [first] = getProblemSearchIndex();

    expect(getProblemSearchIndex()).not.toHaveLength(0);
    expect(first).toEqual(expect.objectContaining({
      provider: expect.any(String),
      problemId: expect.any(String),
      title: expect.any(String),
      difficulty: expect.any(String),
      sourceUrl: expect.any(String),
    }));
    expect(first).not.toHaveProperty("problemKey");
    expect(first).not.toHaveProperty("slug");
  });
});
