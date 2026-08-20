import java.util.Arrays;

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = {};
        for (int[] command : commands) {
            int i = command[0];
            int j = command[1];
            int k = command[2];
            int[] temp = new int[j - i + 1];
            for (int m = 0; m < temp.length; m++) {
                temp[m] = array[i + m - 1];
            }
            Arrays.sort(temp);
            answer = Arrays.copyOf(answer, answer.length + 1);
            answer[answer.length - 1] = temp[k - 1];
        }
        return answer;
    }
}