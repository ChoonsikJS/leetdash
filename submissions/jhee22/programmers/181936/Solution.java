class Solution {
    public int solution(int number, int n, int m) {
        // 삼항연산자 연습 
        return (number % n == 0 && number % m == 0) ? 1 : 0 ; 
    }
}