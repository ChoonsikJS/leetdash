class Solution {
    public int solution(int[] num_list) {
        // 351 + 42 
        int oddSum  = 0; 
        int evenSum = 0; 
        for (int num : num_list) {
            if (num % 2 == 0 ) {
                // 숫자 붙이는 logic 
                // evenSum = 0 * 10 + 4 , 4
                // evenSum = 4  * 10 + 2, 42 ... 아하 
                evenSum = evenSum * 10 + num; 
            } else {
                oddSum = oddSum * 10 + num; 
            }
        }
        return evenSum + oddSum; 
    }
}