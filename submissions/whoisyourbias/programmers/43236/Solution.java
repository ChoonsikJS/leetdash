private int bs(long from, long to, int n, ArrayList<Integer> distances) {
    int answer = 0;

    while (from <= to) {
        long mid = (from + to) / 2;

        int removedRocks = 0;
        ArrayList<Integer> cloned = new ArrayList<>(distances);

        int i = 0;

        while (i < cloned.size() - 1) {
            if (cloned.get(i) < mid) {
                cloned.set(i + 1,
                        cloned.get(i) + cloned.get(i + 1));

                cloned.remove(i);

                i = Math.max(0, --i);
                removedRocks++;
            } else {
                i++;
            }
        }

        if (removedRocks <= n) {
            // mid 가능 → 더 큰 값 탐색
            answer = (int) mid;
            from = mid + 1;
        } else {
            // mid 불가능 → 더 작은 값 탐색
            to = mid - 1;
        }
    }

    return answer;
}