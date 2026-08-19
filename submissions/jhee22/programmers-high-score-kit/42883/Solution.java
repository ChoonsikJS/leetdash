/*
    number에서 k만큼 수를 제거 했을 때 만들 수 있는 가장 큰수 return 
*/
import java.util.*;
class Solution {
    public String solution(String number, int k) {
        Deque<Character> stack = new ArrayDeque<>(); 
        int removed = 0; 
        // 현재 숫자가 더 크면, 바로 앞의 작은 숫자를 제거
        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i); 
            while (!stack.isEmpty() && removed < k && stack.peekLast() < c) {
                stack.pollLast(); 
                removed++; 
            }
            stack.addLast(c); 
        }
        int last = k - removed; 
        for (int i = 0; i < last; i++) {
            stack.pollLast(); 
        }
        // stack -> String 
        StringBuilder sb = new StringBuilder(); 
        for (char c : stack) {
            sb.append(c); 
        }
        return sb.toString(); 
    }
}