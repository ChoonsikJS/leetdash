class Solution {
    public int[] solution(int money) {
        int aa = money / 5500;
    	int left=money - aa*5500;
    	int[] answer = {aa,left};
        return answer;
    }
}