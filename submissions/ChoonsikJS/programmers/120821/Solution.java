class Solution {
    public int[] solution(int[] num_list) {
        int[] answer = new int[num_list.length];
        int idx=0;
        for (int i = answer.length-1; i >= 0 ; i--) {
			answer[idx++]=num_list[i];}
        return answer;
    }
}