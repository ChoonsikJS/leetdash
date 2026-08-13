class Solution {
    public int[] plusOne(int[] digits) {
        int plus = 1;

        for (int i = digits.length - 1; i >= 0; i--) {
            int sum = digits[i] + plus;
            digits[i] = sum % 10;
            plus = sum / 10;

            if (plus == 0) return digits;
        }

        int[] result = new int [digits.length + 1];
        result[0] = 1;
        System.arraycopy(result, 1, digits, 0, digits.length);
        return result;
    }
}