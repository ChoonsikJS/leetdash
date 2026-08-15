"use client";

import { useEffect, useState } from "react";
import { Search } from "lucide-react";
import { getProblemSearchHref } from "@/lib/routes";

export function ProblemSearchForm() {
  const [query, setQuery] = useState("");

  useEffect(() => {
    const readQuery = () => {
      const params = new URLSearchParams(window.location.search);
      setQuery(window.location.pathname.replace(/\/+$/, "").endsWith("/search") ? params.get("q") ?? "" : "");
    };

    readQuery();
    window.addEventListener("popstate", readQuery);
    return () => window.removeEventListener("popstate", readQuery);
  }, []);

  return (
    <form className="shell-search" action={getProblemSearchHref()} method="get" role="search">
      <label className="sr-only" htmlFor="global-problem-search">문제 검색</label>
      <input
        id="global-problem-search"
        name="q"
        type="search"
        value={query}
        onChange={(event) => setQuery(event.target.value)}
        placeholder="문제 번호 또는 제목 검색"
        autoComplete="off"
      />
      <button type="submit" aria-label="문제 검색">
        <Search size={17} aria-hidden="true" />
      </button>
    </form>
  );
}
