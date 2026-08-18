import { describe, expect, it } from "vitest";
import {
  buildProblemRecommendationPrompt,
  getProblemRecommendationHref,
  getUserSubmissionRoute,
} from "@/lib/problem-recommendation-link";

function expectGroundedRecommendationContract(prompt: string) {
  expect(prompt).toContain("현재 공식 문제 페이지, 저장소 카탈로그, 제출 파일/meta.json, solution 코드 추론 순");
  expect(prompt).toContain("충돌 시 공식 페이지를 우선");
  expect(prompt).toContain("플랫폼 Accepted로 표현하지 않는다");
  expect(prompt).toContain("Accepted 증거가 없으면 unspecified");
  expect(prompt).toContain("submittedAt은 Git commit 시각");
  expect(prompt).toContain("확인할 수 없는 값은 임의로 채우지 말고 정확히 unspecified");
  expect(prompt).toContain("LeetCode와 Programmers에서 서로 독립적으로 3개씩");
  expect(prompt).toContain("다른 플랫폼이나 부적합한 문제로 채우지 않는다");
  expect(prompt).toContain("6주, 주 5회 세션");
  expect(prompt).toContain("관측 근거");
  expect(prompt).toContain("신뢰도");
  expect(prompt).toContain("추천 이유");
  expect(prompt).toContain("다음 행동");
}

describe("problem recommendation link", () => {
  it("points at the user's submissions directory on the master branch", () => {
    expect(getUserSubmissionRoute("submissions/ada user")).toBe(
      "https://github.com/whoisyourbias/leetdash/tree/master/submissions/ada%20user",
    );
  });

  it("defines a grounded study analysis and curriculum", () => {
    const prompt = buildProblemRecommendationPrompt("submissions/ada");

    expect(prompt).toContain(getUserSubmissionRoute("submissions/ada"));
    expect(prompt).toContain("data/problem-catalog.json");
    expect(prompt).toContain("같은 problemKey의 중복 제출은 하나로 합치고");
    expect(prompt).toContain("이미 푼 문제는 신규 추천에서 제외");
    expect(prompt).toContain("숙련도 부족으로 단정하지 않는다");
    expect(prompt).toContain("6주 커리큘럼");
    expectGroundedRecommendationContract(prompt);
  });

  it("uses an evidence-aware beginner plan when there are no submissions", () => {
    const prompt = buildProblemRecommendationPrompt("submissions/new-user", false);

    expect(prompt).not.toContain(getUserSubmissionRoute("submissions/new-user"));
    expect(prompt).toContain("아직 제출 이력이 없는 입문자");
    expect(prompt).toContain("실력과 취약점을 추측하지 않는다");
    expect(prompt).toContain("정답 코드는 제공하지 않는다");
    expectGroundedRecommendationContract(prompt);
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
