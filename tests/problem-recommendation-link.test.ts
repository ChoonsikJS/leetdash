import { describe, expect, it } from "vitest";
import {
  buildProblemRecommendationPrompt,
  getProblemRecommendationHref,
  getUserSubmissionRoute,
} from "@/lib/problem-recommendation-link";

function expectBalancedPlatformRecommendations(prompt: string) {
  expect(prompt).toContain("LeetCode와 Programmers만 추천 대상으로 삼고");
  expect(prompt).toContain("두 플랫폼의 후보를 서로 독립적으로 선정한다");
  expect(prompt).toContain("LeetCode 3개와 Programmers 3개를 추천한다");
  expect(prompt).toContain("3개보다 적으면 확인된 문제까지만 추천");
  expect(prompt).toContain("해당 플랫폼에는 더 추천할 문제가 없다고 명시한다");
  expect(prompt).toContain("부족한 수를 다른 플랫폼 문제나 추천 기준에 맞지 않는 문제로 억지로 채우지 않는다");
  expect(prompt).toContain("플랫폼별 추천 개수와 부족 여부");
}

describe("problem recommendation link", () => {
  it("points at the user's submissions directory on the master branch", () => {
    expect(getUserSubmissionRoute("submissions/ada user")).toBe(
      "https://github.com/whoisyourbias/leetdash/tree/master/submissions/ada%20user",
    );
  });

  it("defines a grounded analysis and recommendation procedure", () => {
    const prompt = buildProblemRecommendationPrompt("submissions/ada");

    expect(prompt).toContain("https://github.com/whoisyourbias/leetdash/tree/master/submissions/ada");
    expect(prompt).toContain("모든 하위 폴더를 탐색");
    expect(prompt).toContain("같은 문제의 중복 제출은 하나의 고유 문제로 합친다");
    expect(prompt).toContain("주 유형만 사용하여 합계가 100%가 되게 한다");
    expect(prompt).toContain("숙련도 부족이 아니라 학습 데이터의 공백 또는 낮은 노출로 표현한다");
    expect(prompt).toContain("이미 푼 문제는 추천하지 않는다");
    expect(prompt).toContain("Mermaid pie 차트");
    expect(prompt).toContain("미풀이 문제를 최대 6개");
    expect(prompt).toContain("확인하지 못한 사실이나 문제를 만들어내지 않는다");
    expectBalancedPlatformRecommendations(prompt);
  });

  it("recommends a beginner path without opening a missing repository for a new user", () => {
    const prompt = buildProblemRecommendationPrompt("submissions/new-user", false);

    expect(prompt).not.toContain(getUserSubmissionRoute("submissions/new-user"));
    expect(prompt).toContain("아직 제출한 풀이가 없는 처음 시작하는 사용자");
    expect(prompt).toContain("사용자 제출 경로를 열거나 풀이 이력을 분석하지 않는다");
    expect(prompt).toContain("기초 유형을 단계적으로 익힐 수 있는 문제 6개");
    expect(prompt).toContain("가장 먼저 풀 문제 1개");
    expect(prompt).toContain("정답 코드");
    expectBalancedPlatformRecommendations(prompt);
  });

  it("opens ChatGPT search with the complete prompt", () => {
    const href = getProblemRecommendationHref("submissions/ada");
    const url = new URL(href);

    expect(url.origin).toBe("https://chatgpt.com");
    expect(url.searchParams.get("hints")).toBe("search");
    expect(url.searchParams.get("q")).toBe(buildProblemRecommendationPrompt("submissions/ada"));
    expect(href.length).toBeLessThan(8_000);
  });

  it("opens ChatGPT search with the new-user prompt when there are no submissions", () => {
    const href = getProblemRecommendationHref("submissions/new-user", false);
    const url = new URL(href);

    expect(url.searchParams.get("q")).toBe(
      buildProblemRecommendationPrompt("submissions/new-user", false),
    );
    expect(url.searchParams.get("q")).not.toContain(
      getUserSubmissionRoute("submissions/new-user"),
    );
  });
});
