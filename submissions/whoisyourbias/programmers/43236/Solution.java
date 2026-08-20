import java.util.*;

class Solution {
    public int solution(int distance, int[] rocks, int n) {
        Arrays.sort(rocks);
        return bs(0, distance, n, rocks, distance);
    }
    
    private int bs(long from, long to, int n, int[] rocks, int distance) {
        long answer = 0;

        while (from <= to) {
            long mid = (from + to) / 2;

            int removedRocks = 0;
            int prev = 0;

            for (int rock : rocks) {
                if (rock - prev < mid) {
                    removedRocks++;
                } else {
                    prev = rock;
                }
            }
            
            if (distance - rocks[prev] < mid)
                removedRocks++;

            if (removedRocks <= n) {
                // mid 가능 → 더 큰 값 탐색
                answer = mid;
                from = mid + 1;
            } else {
                // mid 불가능 → 더 작은 값 탐색
                to = mid - 1;
            }
        }

        return (int) answer;
    }
    
}
