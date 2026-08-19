/** @vitest-environment jsdom */

import { cleanup, fireEvent, render, screen } from "@testing-library/react";
import { afterEach, describe, expect, it } from "vitest";
import { DashboardTabs } from "@/app/components/dashboard-tabs";

afterEach(cleanup);

describe("DashboardTabs", () => {
  it("switches between two tabs and sizes the tab list for both", () => {
    render(
      <DashboardTabs
        tabs={[
          { id: "overview", label: "현황", summary: "진행률", children: <div>현황 내용</div> },
          { id: "analysis", label: "분석", summary: "난이도", children: <div>분석 내용</div> },
        ]}
      />,
    );

    const tabList = screen.getByRole("tablist");
    const overviewTab = screen.getByRole("tab", { name: /현황/ });
    const analysisTab = screen.getByRole("tab", { name: /분석/ });

    expect(tabList.classList.contains("dashboard-tab-list-2")).toBe(true);
    expect(overviewTab.getAttribute("aria-selected")).toBe("true");
    expect(screen.getByRole("tabpanel", { name: /현황/ }).hasAttribute("hidden")).toBe(false);

    fireEvent.click(analysisTab);

    expect(analysisTab.getAttribute("aria-selected")).toBe("true");
    expect(screen.getByRole("tabpanel", { name: /분석/ }).hasAttribute("hidden")).toBe(false);
  });
});
