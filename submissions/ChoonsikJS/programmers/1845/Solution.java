import java.util.ArrayList;
import java.util.List;

class Solution {
    public int solution(int[] nums) {
        int answer = 0;
        java.util.HashMap<Integer, Integer> map = new java.util.HashMap<>();
        for (int num : nums) {map.put(num, map.getOrDefault(num, 0) + 1);}
        List<Integer> values = new ArrayList<>(map.values());
        int max = nums.length / 2;
        if (values.size() < max) {
            answer = values.size();
        } else {
            answer = max;
        }
        return answer;
    }
}