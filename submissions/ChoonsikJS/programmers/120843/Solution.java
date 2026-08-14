class Solution {
    public int solution(int[] numbers, int k) {
        int answer = 0;
        // idx > idx +2 : idx +2가 배열의 길이를 넘어가면
        // idx +2 - 배열의 길이 | 만큼을 빼주면 된다.
        int idx = 0;
        while(k > 1) {
            idx += 2;
            if(idx >= numbers.length) idx -= numbers.length;
            k--;
        }
        return numbers[idx];
    }
}