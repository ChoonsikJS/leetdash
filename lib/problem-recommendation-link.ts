const CHATGPT_SEARCH_URL = "https://chatgpt.com/";
const REPOSITORY_TREE_URL = "https://github.com/whoisyourbias/leetdash/tree/master";
const PROBLEM_CATALOG_URL =
  "https://github.com/whoisyourbias/leetdash/blob/master/data/problem-catalog.json";

const PLATFORM_RECOMMENDATION_RULES = [
  "추천은 LeetCode와 Programmers에서 서로 독립적으로 3개씩 선정한다.",
  "적합한 미풀이 문제가 3개보다 적으면 확인된 문제만 추천하고 부족 사유를 밝힌다. 다른 플랫폼이나 부적합한 문제로 채우지 않는다.",
  "각 추천에 플랫폼, 문제 ID와 이름, 공식 난이도, 핵심 유형, 공식 링크, 추천 이유를 표시한다.",
];

const EVIDENCE_RULES = [
  "문제 정보는 현재 공식 문제 페이지, 저장소 카탈로그, 제출 파일/meta.json, solution 코드 추론 순으로 신뢰한다. 충돌 시 공식 페이지를 우선하고 충돌을 표시한다.",
  "실제 풀이 이력은 저장소 증거를 우선한다. solution.*가 있으면 저장소 기준 SOLVED로 집계하되 플랫폼 Accepted로 표현하지 않는다. Accepted 증거가 없으면 unspecified이다.",
  "solvedAt은 명시된 풀이 날짜이고 submittedAt은 Git commit 시각이다. 둘을 플랫폼 제출 시각으로 혼동하지 않는다.",
  "확장자로 확인되는 언어는 .java=Java, .py=Python, .js=JavaScript, .ts=TypeScript로 기록한다. 코드에서 추론한 유형은 solution code inference라고 표시한다.",
  "확인할 수 없는 값은 임의로 채우지 말고 정확히 unspecified로 기록한다.",
];

export function getUserSubmissionRoute(submissionsPath: string): string {
  const encodedPath = submissionsPath
    .split("/")
    .filter(Boolean)
    .map((segment) => encodeURIComponent(segment))
    .join("/");

  return `${REPOSITORY_TREE_URL}/${encodedPath}`;
}

function buildNewUserProblemRecommendationPrompt(): string {
  return [
    "기업 코딩테스트 학습 전략가로서 아직 제출 이력이 없는 입문자에게 학습 계획을 제시하라.",
    "존재하지 않는 사용자 제출 경로를 열거나 실력과 취약점을 추측하지 않는다.",
    `문제 후보의 저장소 카탈로그: ${PROBLEM_CATALOG_URL}`,
    ...EVIDENCE_RULES,
    ...PLATFORM_RECOMMENDATION_RULES,
    "배열/문자열부터 해시, 스택/큐, 투 포인터, 이분 탐색으로 확장되는 순서를 사용한다.",
    "목표 기업과 주당 시간은 unspecified이다. 6주, 주 5회 세션 기준이며 1~2주는 기초, 3~4주는 핵심 유형, 5주는 혼합 문제, 6주는 모의 테스트와 복습으로 구성한다.",
    "한국어로 ① 입력 한계 ② 추천 문제 표 ③ 6주 커리큘럼 ④ 첫 세션 행동 순서로 답하라. 각 판단과 추천에는 관측 근거, 판단, 신뢰도(high/medium/low), 추천 이유, 다음 행동을 검증 가능하게 표시한다. 정답 코드는 제공하지 않는다.",
  ].join("\n");
}

export function buildProblemRecommendationPrompt(
  submissionsPath: string,
  hasSubmissions = true,
): string {
  if (!hasSubmissions) {
    return buildNewUserProblemRecommendationPrompt();
  }

  return [
    "기업 코딩테스트 학습 전략가이자 추천 근거 검증자로서 GitHub 풀이 이력을 분석하라.",
    `사용자 제출 경로(모든 하위 폴더 확인): ${getUserSubmissionRoute(submissionsPath)}`,
    `저장소 문제 카탈로그: ${PROBLEM_CATALOG_URL}`,
    ...EVIDENCE_RULES,
    "같은 problemKey의 중복 제출은 하나로 합치고, 이미 푼 문제는 신규 추천에서 제외한다.",
    "유형별 문제 수와 노출 공백을 분석하되 적은 풀이 수를 숙련도 부족으로 단정하지 않는다. 코드 정확성, 풀이 시간, 실패 횟수 자료가 없으면 실력 판단의 불확실성을 밝힌다.",
    ...PLATFORM_RECOMMENDATION_RULES,
    "목표는 일반 기업 코딩테스트 통과, 목표 기업과 주당 시간은 unspecified, 역할은 software engineer이다. 6주, 주 5회 세션으로 기초 보완→핵심 유형→혼합 문제→모의 테스트/복습 순서의 커리큘럼을 작성한다. 주당 시간이 없으므로 시간 대신 세션 단위로 계획한다.",
    "한국어로 ① 분석 범위와 한계 ② 유형별 관측 근거·판단·신뢰도 ③ 추천 문제 표와 추천 이유 ④ 6주 커리큘럼 ⑤ 사용자의 다음 행동 순서로 답하라. 확인하지 못한 사실이나 문제를 만들지 않는다.",
  ].join("\n");
}

export function getProblemRecommendationHref(
  submissionsPath: string,
  hasSubmissions = true,
): string {
  const url = new URL(CHATGPT_SEARCH_URL);
  url.searchParams.set("hints", "search");
  url.searchParams.set("q", buildProblemRecommendationPrompt(submissionsPath, hasSubmissions));
  return url.toString();
}
