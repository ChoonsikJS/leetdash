/** @vitest-environment jsdom */

import { cleanup, render, screen, within } from "@testing-library/react";
import { afterEach, describe, expect, it } from "vitest";
import { UserDifficultyAnalysis } from "@/app/components/user-difficulty-analysis";
import type { ProviderDifficultyAnalysis } from "@/lib/progress";

afterEach(cleanup);

describe("UserDifficultyAnalysis", () => {
  it("shows solved and zero-count difficulties for a provider with solved problems", () => {
    const analysis: ProviderDifficultyAnalysis[] = [
      {
        provider: "leetcode",
        title: "LeetCode",
        solvedTotal: 2,
        difficulties: [
          { difficulty: "easy", label: "쉬움", solved: 2 },
          { difficulty: "medium", label: "보통", solved: 0 },
          { difficulty: "hard", label: "어려움", solved: 0 },
        ],
      },
    ];

    render(<UserDifficultyAnalysis analysis={analysis} userName="Ada" />);

    expect(screen.getByText("Ada님이 풀이 완료한 문제를 공급자별 난이도로 나누어 표시합니다.")).not.toBeNull();
    expect(screen.getByText("총 2개")).not.toBeNull();
    const difficultyList = screen.getByRole("list", { name: "LeetCode 난이도별 풀이 수" });
    expect(within(difficultyList).getByText("쉬움")).not.toBeNull();
    expect(within(difficultyList).getAllByText("0개")).toHaveLength(2);
  });

  it("shows an empty state instead of difficulty bars when a provider has no solved problems", () => {
    const analysis: ProviderDifficultyAnalysis[] = [
      {
        provider: "programmers",
        title: "Programmers",
        solvedTotal: 0,
        difficulties: [{ difficulty: "level-0", label: "Level 0", solved: 0 }],
      },
    ];

    render(<UserDifficultyAnalysis analysis={analysis} userName="Ada" />);

    expect(screen.getByText("아직 풀이 완료한 문제가 없습니다.")).not.toBeNull();
    expect(screen.queryByRole("list", { name: "Programmers 난이도별 풀이 수" })).toBeNull();
  });
});
