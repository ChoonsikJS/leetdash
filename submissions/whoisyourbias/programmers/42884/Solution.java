import java.util.*;

class Solution {
    public int solution(int[][] routes) {
        int answer = 0;
        
        Arrays.sort(routes, (a, b) -> a[1] - b[1]);
        
        for (int[] route: routes) {
            System.out.printf("%d %d | ", route[0], route[1]);
        }
        
        
        for (int i = 1; i < routes.length; i++) {
            if (routes[i - 1][1] >= routes[i][0]) {
                answer++;
                i++;
            } else {
                answer++;
            }
        }
        
        return answer;
    }
}