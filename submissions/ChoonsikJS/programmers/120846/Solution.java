class Solution {
    public int solution(int n) {
        int answer = 0;
        // 1부터 n까지의 소수
        for(int i=1; i<=n; i++) {
            int count = 0;
            for(int j=1; j<=i; j++) {
                if(i%j==0) count++;
            }
            if(count==2) answer++;
        }
        return n-answer-1; // 1 소수,합성수가 아님
    }
}