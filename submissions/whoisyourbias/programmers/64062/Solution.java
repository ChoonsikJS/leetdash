import java.util.*;

class Solution {
    public int solution(int[] stones, int k) {
        // SLIDING WINDOW?
        // Monotonic Queue        
        Deque<Integer> dq = new ArrayDeque<>();

        int answer = Integer.MAX_VALUE;
        
        for (int i = 0; i < stones.length; i++) {
            // window 크기 유지
            while (!dq.isEmpty() && dq.peekFirst() <= i - k)
                dq.pollFirst();
            
            // remove smaller from last
            while (!dq.isEmpty() && stones[dq.peekLast()] <= stones[i])
                dq.pollLast();
            
            dq.offerLast(i);
            
            // window 크기 맞으면
            if (i >= k - 1) {
                answer = Math.min(answer, stones[dq.peekFirst()]);
            }
        }
        
        return answer;
    }
}
