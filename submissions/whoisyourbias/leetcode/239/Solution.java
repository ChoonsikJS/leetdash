import java.util.*;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] answer = new int[nums.length - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();
        int answeri = 0;
        for (int i = 0 ; i < nums.length; i++) {

            // 현재 윈도우 바깥 제거
            while (!dq.isEmpty() && dq.peekFirst() <= i-k)
                dq.pollFirst();

            // 현재 값보다 작은 애들 뒤에서 제거 이후
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i])
                dq.pollLast();
            // 삽입
            dq.offerLast(i);

            if (i + 1>= k)
                answer[answeri++] = nums[dq.peekFirst()];
            // System.out.println(Arrays.toString(answer));
        }
        return answer;
    }
}