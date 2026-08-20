
import java.util.*;
class Solution {
    public int solution(int[] arr) {
        int answer = 0;

        int n = arr.length;
        answer += arr[0];
        for (int i = 1 ; i  <arr.length; i++) {
            if (arr[i] > arr[i - 1]) {
                
            } else{
                arr[i] += arr[i - 1];
            }
        }
        
        return arr[n - 1];
    }
    
    private int safeIndexer(int n, int i) {
        if (i == -1)
            return n - 1;
        
        if (i == n)
            return 0;
        
        return i;
    }
}