import { getProblemSearchIndex } from "@/lib/problem-search";

export const dynamic = "force-static";

export function GET() {
  return Response.json({ items: getProblemSearchIndex() }, {
    headers: {
      "Cache-Control": "public, max-age=600",
    },
  });
}
