import java.util.PriorityQueue;

class Solution {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int x : scoville) {
            pq.add(x);
        }

        while(pq.peek() < K) {
            if(pq.size() == 1) {
                return -1;
            }
            int first = pq.poll();
            int second = pq.poll();
            int newS = first + (second * 2);
            pq.add(newS);
            answer++;
        }
        
        
        return answer;
    }
}