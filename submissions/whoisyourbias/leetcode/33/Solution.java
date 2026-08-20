import java.util.*;

class Solution {
    public int search(int[] nums, int target) {

        int before = nums[0];
        int k = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > before) {
                before = nums[i];
                continue;
            } else {
                k = i;
                break;
            }
        }

        int left = Arrays.binarySearch(nums, 0, k, target);
        int right = Arrays.binarySearch(nums, k, nums.length, target);
        if (left < 0 && right < 0)
            return -1;
        else {
            return left < 0 ? right : left;
        }
    }
}