import java.util.*;

public class Solution {
    public int[] solution(int []arr) {
        int[] answer = {};
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                list.add(arr[i]);
            } else {
                if (arr[i] != arr[i - 1]) {
                    list.add(arr[i]);
                }
            }
        }
        answer = list.stream().mapToInt(i -> i).toArray();
        // [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
        System.out.println("Hello Java");

        return answer;
    }
}