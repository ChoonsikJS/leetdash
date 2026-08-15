import type { ProviderDifficultyAnalysis } from "@/lib/progress";

export function UserDifficultyAnalysis({
  analysis,
  userName,
}: {
  analysis: ProviderDifficultyAnalysis[];
  userName: string;
}) {
  return (
    <section aria-labelledby="user-difficulty-analysis-title">
      <div className="section-heading difficulty-analysis-heading">
        <p className="eyebrow">DIFFICULTY</p>
        <h2 id="user-difficulty-analysis-title">난이도별 풀이 분포</h2>
        <p className="section-description">
          {userName}님이 풀이 완료한 문제를 공급자별 난이도로 나누어 표시합니다.
        </p>
      </div>

      <div className="difficulty-analysis-grid">
        {analysis.map((provider) => {
          const maxSolved = Math.max(...provider.difficulties.map((difficulty) => difficulty.solved), 0);

          return (
            <article className="difficulty-analysis-card" key={provider.provider}>
              <div className="difficulty-analysis-card-header">
                <h3>{provider.title}</h3>
                <span className="difficulty-analysis-total">총 {provider.solvedTotal}개</span>
              </div>

              {provider.solvedTotal === 0 ? (
                <div className="difficulty-analysis-empty">아직 풀이 완료한 문제가 없습니다.</div>
              ) : (
                <ul className="difficulty-analysis-list" aria-label={`${provider.title} 난이도별 풀이 수`}>
                  {provider.difficulties.map((difficulty) => (
                    <li className="difficulty-analysis-item" key={difficulty.difficulty}>
                      <div className="difficulty-analysis-label">
                        <span>{difficulty.label}</span>
                        <strong>{difficulty.solved}개</strong>
                      </div>
                      <div className="difficulty-analysis-bar" aria-hidden="true">
                        <div
                          className="difficulty-analysis-bar-fill"
                          style={{ width: `${maxSolved === 0 ? 0 : (difficulty.solved / maxSolved) * 100}%` }}
                        />
                      </div>
                    </li>
                  ))}
                </ul>
              )}
            </article>
          );
        })}
      </div>
    </section>
  );
}
