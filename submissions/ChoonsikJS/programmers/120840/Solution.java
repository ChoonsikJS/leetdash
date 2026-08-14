import java.math.BigInteger;

class Solution {
    public static int solution(int balls, int share) {
        BigInteger answer = BigInteger.valueOf(0);
        answer = factorial(balls).divide(factorial(share).multiply(factorial(balls - share)));
        return answer.intValue();
    }
    private static BigInteger factorial(int n) {
        // 팩토리얼 재귀함수
        if (n <= 1) return BigInteger.valueOf(1);
        return BigInteger.valueOf(n).multiply(factorial(n - 1));
    }
}
