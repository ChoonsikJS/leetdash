export function getUserProfileHref(
  userId: string,
  basePath = process.env.NEXT_PUBLIC_BASE_PATH ?? "",
) {
  const normalizedBasePath = basePath.replace(/\/+$/, "");
  return `${normalizedBasePath}/users/${encodeURIComponent(userId)}/`;
}

export function getProblemComparisonHref(
  provider: string,
  problemId: string,
  userId?: string,
  basePath = process.env.NEXT_PUBLIC_BASE_PATH ?? "",
) {
  const normalizedBasePath = basePath.replace(/\/+$/, "");
  const userQuery = userId === undefined ? "" : `?user=${encodeURIComponent(userId)}`;
  return `${normalizedBasePath}/problems/${encodeURIComponent(provider)}/${encodeURIComponent(problemId)}/${userQuery}`;
}

export function getProblemSearchHref(
  query?: string,
  basePath = process.env.NEXT_PUBLIC_BASE_PATH ?? "",
) {
  const normalizedBasePath = basePath.replace(/\/+$/, "");
  const searchQuery = query === undefined ? "" : `?q=${encodeURIComponent(query)}`;
  return `${normalizedBasePath}/search/${searchQuery}`;
}

export function getProblemSearchDataUrl(
  basePath = process.env.NEXT_PUBLIC_BASE_PATH ?? "",
) {
  const normalizedBasePath = basePath.replace(/\/+$/, "");
  return `${normalizedBasePath}/problem-search/data.json`;
}
