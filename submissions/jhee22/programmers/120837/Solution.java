/*
    최소한의 병력
    장군 : 5, 병정 : 3, 일 : 1 
*/  

class Solution {
    public int solution(int hp) {
        // answer == 몫 
        int answer = 0;
        answer += hp / 5; 
        hp %= 5; 
        
        answer += hp / 3; 
        hp %= 3; 
        
        answer += hp; 
        return answer;
    }
}