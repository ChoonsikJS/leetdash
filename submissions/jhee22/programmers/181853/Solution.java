// Arrays.copyOf(리스트, 개수)
import java.util.Arrays; 
class Solution {
    public int[] solution(int[] num_list) {
        Arrays.sort(num_list);
        return Arrays.copyOf(num_list, 5);
    }
}