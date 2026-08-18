import java.util.*; 
class Solution {
    public int solution(int[] people, int limit) {
        int cnt = 0;
        // people 정렬
        Arrays.sort(people); 
        
        // 투 포인터 구현 
        int left = 0; 
        int right = people.length - 1; 
        // 두 포인터가 교차하기 직전까지 
        while (left <= right) {
            if (people[left] + people[right] <= limit) {
                left++; 
            } 
            // 가장 무거운 사람
            right--; 
            cnt++; 
        }
        return cnt;
    }
}