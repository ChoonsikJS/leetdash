import java.util.*;

// 1 <= target <= 109
// 1 <= nums.length <= 105
// 1 <= nums[i] <= 104
class Solution {
    public int minSubArrayLen(int target, int[] nums) {

        int l = 0;
        int answer = Integer.MAX_VALUE;

        int sum = 0;
        int r = 0;
        sum += nums[r];
        while (l < nums.length && r < nums.length) {
            if (sum >= target) {
                if (r-l + 1 < answer) {
                    answer = r - l + 1;
                }
                // l 이동
                while (l < nums.length && l < r && sum - nums[l] >= target) {
                    sum -= nums[l];
                    l++;
                }
                
                if ((sum >= target) && (r-l + 1 < answer)) {
                    answer = r - l + 1;
                }
            }    
            
            r++;
            if (r < nums.length)
                sum += nums[r];
        }
        return answer == Integer.MAX_VALUE ? 0 : answer;
    }
}
