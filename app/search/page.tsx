import { ProblemSearchResults } from "@/app/components/problem-search-results";

export default function ProblemSearchPage() {
  return (
    <div className="page">
      <div className="page-header">
        <div>
          <p className="eyebrow">SEARCH</p>
          <h1>문제 검색</h1>
          <p className="lede">전체 Provider의 문제 번호와 제목을 한 번에 검색합니다.</p>
        </div>
      </div>

      <ProblemSearchResults />
    </div>
  );
}
