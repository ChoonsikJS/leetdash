import java.util.*;

class Solution {
    public int solution(int[] nums) {
        int answer = 0;
        
        HashMap<Integer,List<Integer>> map = new HashMap<>();
        for (int x : nums) {
            if (!map.containsKey(x)) {
                map.put(x, new ArrayList<>());
              }
         map.get(x).add(x);
        }
        
        int l = nums.length / 2;
        
        if( l == map.keySet().size()) {
            return l;
        } else if(l > map.keySet().size()) {
            answer = map.keySet().size();
        } else if (l < map.keySet().size()) {
            answer = l;
        }
        return answer;

    }
}