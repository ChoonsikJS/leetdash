import java.util.*;

class Solution {
    static ArrayList<Integer[]> lst = new ArrayList<>();
    public int[][] solution(int n) {
        int[][] answer = {};
        
        hanoi(n, 1, 3, 2);
        
        answer = new int[lst.size()][2];
        for (int i = 0 ; i < lst.size(); i++) {
            answer[i][0] = lst.get(i)[0];
            answer[i][1] = lst.get(i)[1];
        }
        
        return answer;
    }
    
    public void hanoi(int n, int from, int to, int left) {

        if (n == 1) {
            Integer[] a = new Integer[2];
            a[0] = from;
            a[1] = to;
            lst.add(a);
            return;
        }
            
        
        
        /*
        가장 큰 원판을 N이라 했을때,
        N - 1개의 원판
        N번째 원판
        이 그룹으로 나누어

        N   N-1
        A    B    C

            N-1   N
        A    B    C

                 N-1
                  N
        A    B    C

        이 분할정복.

        */
        hanoi(n - 1, from, left, to);
        Integer[] a = new Integer[2];
        a[0] = from;
        a[1] = to;
        lst.add(a);
        hanoi(n - 1, left, to, from);
    }
}