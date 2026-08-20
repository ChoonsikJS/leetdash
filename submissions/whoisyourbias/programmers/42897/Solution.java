
import java.util.*;
class Solution {
    static int n;
    public int solution(int[] arr) {
        int answer = 0;

        n = arr.length;
        
        return Math.max(
            maxDP(0, n - 1, arr.clone()),
            maxDP(1, n, arr.clone())
        );
    }
    
    private int maxDP(int from, int to, int[] arr) {
        arr[from + 1] = Math.max(arr[from], arr[from + 1]);
        for (int i = from + 2; i < to; i++) {
            // 1열배치시 
            // 현재 선택
            if (arr[i] + arr[i - 2] > arr[i - 1]) {
                arr[i] += arr[i-2];
            } else {
                arr[i] = arr[i-1];
            }
        }
        return arr[to - 1];
    }
}