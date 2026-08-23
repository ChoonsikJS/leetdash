import { describe, expect, it } from "vitest";
import {
  catalog,
  getListProblems,
  getProblem,
  getProblemSourceUrl,
  mergeDynamicProblems,
  problemByKey,
  type ProblemCatalog,
} from "@/lib/catalog";

describe("problem catalog", () => {
  it("merges dynamic SWEA problems into the provider catalog without replacing static metadata", () => {
    const base = {
      generatedAt: "2026-08-23T00:00:00.000Z",
      sources: [],
      problems: [
        {
          provider: "swea",
          problemId: "1206",
          problemKey: "swea:1206",
          title: "View",
          difficulty: "D3",
          sourceUrl: "https://swexpertacademy.com/1206",
        },
      ],
      lists: [
        {
          key: "swea",
          title: "SWEA",
          url: "https://swexpertacademy.com",
          summary: [],
          problems: [],
          items: [],
        },
      ],
    } as ProblemCatalog;

    const merged = mergeDynamicProblems(base, [
      {
        provider: "swea",
        problemId: "76543210",
        problemKey: "swea:76543210",
        title: "동적 문제",
        difficulty: "Unknown",
        sourceUrl: "https://swexpertacademy.com/76543210",
      },
      {
        provider: "swea",
        problemId: "1206",
        problemKey: "swea:1206",
        title: "덮어쓰면 안 됨",
        difficulty: "Unknown",
        sourceUrl: "https://swexpertacademy.com/other",
      },
    ]);

    expect(merged.problems.find((problem) => problem.problemKey === "swea:1206")?.title).toBe("View");
    expect(merged.problems.find((problem) => problem.problemKey === "swea:76543210")?.title).toBe("동적 문제");
    expect(merged.lists[0].items.at(-1)).toMatchObject({
      problemKey: "swea:76543210",
      submissionKey: "76543210",
    });
  });

  it("loads the planned provider lists with expected counts", () => {
    expect(catalog.lists.map((list) => [list.key, list.items.length])).toEqual([
      ["top-interview-easy", 49],
      ["leetcode-75", 75],
      ["top-interview-150", 150],
      ["programmers", 689],
      ["swea", 1160],
      ["programmers-high-score-kit", 47],
      ["leetcode", 4029],
    ]);
  });

  it("uses provider-scoped canonical problem identities", () => {
    const problemKeys = new Set<string>();

    for (const problem of catalog.problems) {
      expect(["leetcode", "programmers", "swea"]).toContain(problem.provider);
      expect(problem.problemId).toMatch(/^\S+$/);
      expect(problem.problemKey).toBe(`${problem.provider}:${problem.problemId}`);
      expect(problem.sourceUrl).toMatch(/^https:\/\//);
      expect(problemKeys.has(problem.problemKey)).toBe(false);
      problemKeys.add(problem.problemKey);

      if (problem.provider === "leetcode") {
        expect(problem.slug).toMatch(/^\S+$/);
      } else {
        expect(problem).not.toHaveProperty("slug");
      }
    }

    expect(problemByKey.size).toBe(catalog.problems.length);
  });

  it("has a canonical problem entry for every list item", () => {
    for (const list of catalog.lists) {
      for (const item of getListProblems(list)) {
        expect(problemByKey.has(item.problemKey)).toBe(true);
        expect(item.problem.problemKey).toBe(item.problemKey);
      }
    }
  });

  it("has a numeric, per-list unique submission key for every list item", () => {
    for (const list of catalog.lists) {
      const submissionKeys = new Set<string>();
      for (const item of list.items) {
        expect(item.submissionKey).toMatch(/^\d+$/);
        expect(submissionKeys.has(item.submissionKey)).toBe(false);
        submissionKeys.add(item.submissionKey);
      }
    }
  });

  it("uses problem numbers as LeetCode submission keys", () => {
    const topInterviewEasy = catalog.lists.find((list) => list.key === "top-interview-easy");
    const leetcode75 = catalog.lists.find((list) => list.key === "leetcode-75");
    const topInterview150 = catalog.lists.find((list) => list.key === "top-interview-150");

    expect(topInterviewEasy?.items.find((item) => item.problemKey === "leetcode:66")?.submissionKey).toBe("66");
    expect(topInterviewEasy?.items.find((item) => item.problemKey === "leetcode:1")?.submissionKey).toBe("1");
    for (const item of topInterviewEasy?.items ?? []) {
      expect(item.submissionKey).toBe(problemByKey.get(item.problemKey)?.problemId);
    }

    expect(leetcode75?.items.find((item) => item.problemKey === "leetcode:1768")?.submissionKey).toBe("1768");
    expect(topInterview150?.items.find((item) => item.problemKey === "leetcode:88")?.submissionKey).toBe("88");
  });

  it("includes the complete LeetCode provider catalog", () => {
    const leetcode = catalog.lists.find((list) => list.key === "leetcode");
    expect(leetcode).toBeDefined();
    expect(leetcode?.items.length).toBeGreaterThan(3000);
    expect(leetcode?.items.length).toBe(leetcode?.problems.length);
    expect(leetcode?.items[0]?.submissionKey).toBe("1");
    expect(leetcode?.items.every((item) => item.problemKey.startsWith("leetcode:"))).toBe(true);
  });

  it("includes the initial Programmers and SWEA problems", () => {
    expect(problemByKey.get("programmers:12906")).toMatchObject({
      title: "같은 숫자는 싫어",
      difficulty: "level-1",
    });
    expect(problemByKey.get("swea:1206")).toMatchObject({
      title: "[S/W 문제해결 기본] 1일차 - View",
      difficulty: "D3",
    });
    expect(problemByKey.get("swea:4012")).toMatchObject({
      title: "[모의 SW 역량테스트] 요리사",
      difficulty: "Unknown",
    });

    expect(getProblem("programmers:12906")).toBe(problemByKey.get("programmers:12906"));
    expect(getProblemSourceUrl("programmers:12906")).toBe(
      "https://school.programmers.co.kr/learn/courses/30/lessons/12906",
    );
    expect(getProblemSourceUrl("swea:1206")).toBe(
      "https://swexpertacademy.com/main/code/problem/problemDetail.do?problemId=1206",
    );
  });
});
