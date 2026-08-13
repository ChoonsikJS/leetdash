import java.util.*;

public class Solution {
    public int[] solution(int []arr) {
        
        Stack<Integer> s = new Stack<>();
        for(int x : arr) {
            if(s.empty() || s.peek() != x) {
                s.push(x);
            }
        }
        int[] answer = new int [s.size()];
        for(int i = 0; i < answer.length; i++) {
            answer[i] = s.get(i);
        }
        
        return answer;
    }
}