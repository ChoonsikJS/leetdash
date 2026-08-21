/** @vitest-environment jsdom */

import { cleanup, render, screen } from "@testing-library/react";
import { afterEach, describe, expect, it } from "vitest";
import { UserProblemHistory } from "@/app/components/user-problem-history";
import { SubmissionStatus } from "@/lib/types";

afterEach(cleanup);

describe("UserProblemHistory", () => {
  it("shows each problem difficulty", () => {
    render(
      <UserProblemHistory
        userId="ada"
        history={[
          {
            id: "ada:leetcode:1",
            problemKey: "leetcode:1",
            problemTitle: "Two Sum",
            problemProvider: "leetcode",
            problemId: "1",
            difficulty: "easy",
            sourceKey: "top-interview-easy",
            listTitle: "Top Interview Questions: Easy Collection",
            status: SubmissionStatus.SOLVED,
          },
        ]}
      />,
    );

    expect(screen.getByRole("columnheader", { name: "난이도" })).not.toBeNull();
    expect(screen.getByText("쉬움")).not.toBeNull();
  });
});
