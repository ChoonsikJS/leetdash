"use client";

import { useEffect, useMemo, useState } from "react";
import { ExternalLink } from "lucide-react";
import { difficultyLabel } from "@/lib/format";
import {
  problemSearchProviderLabels,
  searchProblems,
  type ProblemSearchItem,
} from "@/lib/problem-search";
import { getProblemSearchDataUrl } from "@/lib/routes";
import type { CatalogProvider } from "@/lib/catalog";

const PAGE_SIZE = 50;

function readSearchQuery() {
  return new URLSearchParams(window.location.search).get("q") ?? "";
}

export function ProblemSearchResults() {
  const [query, setQuery] = useState<string | null>(null);
  const [items, setItems] = useState<ProblemSearchItem[] | null>(null);
  const [loadError, setLoadError] = useState(false);
  const [visibleCounts, setVisibleCounts] = useState<Partial<Record<CatalogProvider, number>>>({});

  useEffect(() => {
    const updateQuery = () => setQuery(readSearchQuery());
    updateQuery();
    window.addEventListener("popstate", updateQuery);
    return () => window.removeEventListener("popstate", updateQuery);
  }, []);

  useEffect(() => {
    if (!query?.trim() || items !== null) return;

    const controller = new AbortController();
    setLoadError(false);
    fetch(getProblemSearchDataUrl(), { signal: controller.signal })
      .then(async (response) => {
        if (!response.ok) throw new Error(`Problem search data request failed: ${response.status}`);
        const payload = await response.json() as { items?: ProblemSearchItem[] };
        if (!Array.isArray(payload.items)) throw new Error("Problem search data response is invalid");
        setItems(payload.items);
      })
      .catch((error: unknown) => {
        if (error instanceof DOMException && error.name === "AbortError") return;
        setLoadError(true);
      });

    return () => controller.abort();
  }, [items, query]);

  useEffect(() => {
    setVisibleCounts({});
  }, [query]);

  const groups = useMemo(
    () => items && query ? searchProblems(items, query) : [],
    [items, query],
  );
  const totalMatches = groups.reduce((sum, group) => sum + group.items.length, 0);

  if (query === null) {
    return <div className="empty">검색어를 확인하고 있습니다.</div>;
  }

  if (!query.trim()) {
    return <div className="empty">상단 검색창에 문제 번호 또는 제목을 입력해 주세요.</div>;
  }

  if (loadError) {
    return <div className="empty">검색 데이터를 불러오지 못했습니다. 페이지를 새로고침해 주세요.</div>;
  }

  if (items === null) {
    return <div className="empty">문제를 검색하고 있습니다.</div>;
  }

  if (groups.length === 0) {
    return <div className="empty">“{query.trim()}”에 해당하는 문제가 없습니다.</div>;
  }

  return (
    <div className="problem-search-results">
      <p className="problem-search-summary">
        <strong>“{query.trim()}”</strong> 검색 결과 {totalMatches.toLocaleString("ko-KR")}개
      </p>
      {groups.map((group) => {
        const visibleCount = visibleCounts[group.provider] ?? PAGE_SIZE;
        const visibleItems = group.items.slice(0, visibleCount);
        const remainingCount = group.items.length - visibleItems.length;

        return (
          <section className="panel problem-search-provider" aria-labelledby={`search-${group.provider}`} key={group.provider}>
            <div className="panel-header">
              <div>
                <p className="eyebrow">PROVIDER</p>
                <h2 id={`search-${group.provider}`}>{problemSearchProviderLabels[group.provider]}</h2>
                <p className="panel-subtitle">일치하는 문제 {group.items.length.toLocaleString("ko-KR")}개</p>
              </div>
            </div>
            <div className="table-wrap">
              <table>
                <thead><tr><th>#</th><th>문제</th><th>난이도</th><th>링크</th></tr></thead>
                <tbody>
                  {visibleItems.map((item) => (
                    <tr key={`${item.provider}:${item.problemId}`}>
                      <td className="mono">{item.problemId}</td>
                      <td>
                        <a className="problem-link" href={item.sourceUrl} target="_blank" rel="noreferrer">
                          {item.title}
                        </a>
                      </td>
                      <td><span className="badge neutral">{difficultyLabel(item.difficulty)}</span></td>
                      <td>
                        <a className="button catalog-source-button" href={item.sourceUrl} target="_blank" rel="noreferrer">
                          <ExternalLink size={16} aria-hidden="true" />원문
                        </a>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
            {remainingCount > 0 ? (
              <div className="problem-search-more">
                <button
                  className="button"
                  type="button"
                  onClick={() => setVisibleCounts((current) => ({
                    ...current,
                    [group.provider]: visibleCount + PAGE_SIZE,
                  }))}
                >
                  더 보기 ({remainingCount.toLocaleString("ko-KR")}개 남음)
                </button>
              </div>
            ) : null}
          </section>
        );
      })}
    </div>
  );
}
