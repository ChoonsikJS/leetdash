import java.util.*;

class Solution {
    public void moveZeroes(int[] nums) {
        int temp = 0;
        for(int j = 0; j < nums.length; j++) {
            for (int i = 0; i < nums.length - 1; i++) {
                if (nums[i] == 0) {
                    temp = nums[i];
                    nums[i] = nums[i+1];
                    nums[i+1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(nums));
    }
}