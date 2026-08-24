class Solution {
    public int[] plusOne(int[] digits) {
    for (int i = digits.length - 1; i >= 0; i--) {
        if (digits[i] < 9) {
            digits[i]++;
            return digits; // 9가 아니면 1 더하고 바로 종료
        }
        digits[i] = 0; // 9면 0으로 만들고 앞자리로 올림(carry) 계속 진행
    }
    
    // 모든 자리가 9였던 경우 (예: [9, 9, 9] -> [1, 0, 0, 0])
    int[] ans = new int[digits.length + 1];
    ans[0] = 1;
    return ans;
    }
}
