import java.util.HashMap;
import java.util.Map;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> nums1 = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int left = target - nums[i];

            if (nums1.containsKey(left)) {
                return new int[] {nums1.get(left), i};
            }
            nums1.put(nums[i], i);
        }

        return new int[]{};
    }
}