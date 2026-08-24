class Solution {
    public int[] twoSum(int[] nums, int target) {
        int pos1 = 0;
        int pos2 = 1;
        // two-pointer
        while (pos1 < nums.length-1){
            if (nums[pos1]+nums[pos2] == target){
                return new int[] {pos1,pos2};
            }else{
                if (pos2+1 < nums.length){
                    pos2++;
                }else{
                    pos1++;
                    pos2 = pos1+1;
                }
            }
        }
        return null;
    }
}