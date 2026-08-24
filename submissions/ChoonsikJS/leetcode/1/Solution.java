class Solution {
    public int[] twoSum(int[] nums, int target) {
        int pos1 = 0;
        int pos2 = 1;
        // two-pointer
        for(int i=0; i <nums.length; i++){
            if(nums[pos1]+nums[pos2]==target){
                return new int[] {pos1,pos2};
            }else{
                pos1++;
                pos2++;
            }
        }
        return null;
    }
}