import java.util.*; 
class Solution {
    public int[] solution(int[] num_list) {
        // Arrays.copyOf() 
        int[] answer = Arrays.copyOf(num_list, num_list.length+1);
        int idx = num_list.length - 1;  
        if (num_list[idx] > num_list[idx-1] ) {
            answer[idx+1] = num_list[idx] - num_list[idx-1]; 
        } else {
            answer[idx+1] = num_list[idx] * 2; 
        }
        
        return answer;
    }
}