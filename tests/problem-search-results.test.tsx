/** @vitest-environment jsdom */

import { afterEach, describe, expect, it, vi } from "vitest";
import { cleanup, fireEvent, render, screen, waitFor, within } from "@testing-library/react";
import { ProblemSearchForm } from "@/app/components/problem-search-form";
import { ProblemSearchResults } from "@/app/components/problem-search-results";
import type { ProblemSearchItem } from "@/lib/problem-search";

function problem(
  provider: ProblemSearchItem["provider"],
  problemId: string,
  title = `Problem ${problemId}`,
): ProblemSearchItem {
  return {
    provider,
    problemId,
    title,
    difficulty: provider === "leetcode" ? "easy" : "level-1",
    sourceUrl: `https://example.com/${provider}/${problemId}`,
  };
}

function jsonResponse(value: unknown) {
  return new Response(JSON.stringify(value), {
    headers: { "content-type": "application/json" },
  });
}

afterEach(() => {
  cleanup();
  vi.unstubAllGlobals();
  window.history.replaceState({}, "", "/");
});

describe("ProblemSearchForm", () => {
  it("restores the search query on the result page and submits to the static route", async () => {
    window.history.replaceState({}, "", "/search/?q=two%20sum");

    render(<ProblemSearchForm />);

    const input = await screen.findByRole("searchbox", { name: "문제 검색" });
    expect(input).toHaveProperty("value", "two sum");
    expect(input.closest("form")?.getAttribute("action")).toBe("/search/");
    expect(input.getAttribute("name")).toBe("q");
  });
});

describe("ProblemSearchResults", () => {
  it("separates providers, shows 50 rows initially, and expands each provider independently", async () => {
    window.history.replaceState({}, "", "/search/?q=12");
    const items = [
      ...Array.from({ length: 55 }, (_, index) => problem("leetcode", `12${index}`)),
      ...Array.from({ length: 3 }, (_, index) => problem("programmers", `12${index}`)),
    ];
    vi.stubGlobal("fetch", vi.fn(() => Promise.resolve(jsonResponse({ items }))));

    render(<ProblemSearchResults />);

    const leetcodeHeading = await screen.findByRole("heading", { name: "LeetCode" });
    const leetcodeSection = leetcodeHeading.closest("section")!;
    const programmersSection = screen.getByRole("heading", { name: "Programmers" }).closest("section")!;

    expect(within(leetcodeSection).getAllByRole("row")).toHaveLength(51);
    expect(within(programmersSection).getAllByRole("row")).toHaveLength(4);
    expect(screen.queryByRole("heading", { name: "SWEA" })).toBeNull();
    expect(within(leetcodeSection).getByText("일치하는 문제 55개")).not.toBeNull();

    fireEvent.click(within(leetcodeSection).getByRole("button", { name: "더 보기 (5개 남음)" }));

    expect(within(leetcodeSection).getAllByRole("row")).toHaveLength(56);
    expect(within(programmersSection).getAllByRole("row")).toHaveLength(4);

    const sourceLink = within(leetcodeSection).getAllByRole("link", { name: "원문" })[0]!;
    expect(sourceLink.getAttribute("href")).toBe("https://example.com/leetcode/120");
    expect(sourceLink.getAttribute("target")).toBe("_blank");
    expect(sourceLink.getAttribute("rel")).toContain("noreferrer");
  });

  it("does not load data until a non-empty query is provided", async () => {
    window.history.replaceState({}, "", "/search/?q=%20%20");
    const fetchMock = vi.fn();
    vi.stubGlobal("fetch", fetchMock);

    render(<ProblemSearchResults />);

    expect(await screen.findByText("상단 검색창에 문제 번호 또는 제목을 입력해 주세요.")).not.toBeNull();
    expect(fetchMock).not.toHaveBeenCalled();
  });

  it("shows no-result and loading-failure states", async () => {
    window.history.replaceState({}, "", "/search/?q=missing");
    vi.stubGlobal("fetch", vi.fn(() => Promise.resolve(jsonResponse({ items: [] }))));

    const { unmount } = render(<ProblemSearchResults />);
    expect(await screen.findByText("“missing”에 해당하는 문제가 없습니다.")).not.toBeNull();
    unmount();

    vi.stubGlobal("fetch", vi.fn(() => Promise.reject(new Error("offline"))));
    render(<ProblemSearchResults />);

    await waitFor(() => {
      expect(screen.getByText("검색 데이터를 불러오지 못했습니다. 페이지를 새로고침해 주세요.")).not.toBeNull();
    });
  });
});
