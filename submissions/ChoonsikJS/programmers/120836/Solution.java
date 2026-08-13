class Solution {
    public int solution(int n) {
        int answer = 0;
        for(int i=1; i<n+1; i++) {
                if(n%i==0) answer++;
                // n = 20 , i = 1, 2, 4, 5, 10, 20
            }
        return answer;
    }
}