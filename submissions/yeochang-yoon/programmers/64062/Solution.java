import java.util.*;

class Solution {
    public int solution(int[] stones, int k) {

        Deque<Integer> deque = new ArrayDeque<>();

        for(int i = 0; i < k; i++){
            while(!deque.isEmpty() && stones[deque.peekLast()] < stones[i]){
                deque.pollLast();
            }
            deque.offerLast(i);
        }

        int min_max = stones[deque.peekFirst()];

        for(int i = k; i < stones.length; i++){
            if(deque.peekFirst() == i-k){
                deque.pollFirst();
            }

            while(!deque.isEmpty() && stones[deque.peekLast()] < stones[i]){
                deque.pollLast();
            }
            deque.offerLast(i);

            min_max = Math.min(min_max, stones[deque.peekFirst()]);
        }


        int answer = min_max;
        return answer;
    }
}