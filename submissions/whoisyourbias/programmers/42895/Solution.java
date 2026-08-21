import java.util.*;
import java.util.Map.*;

class Solution {
    public int solution(int N, int number) {
        int answer = 0;
        
        ArrayList<HashSet<Integer>> DP = new ArrayList<>();
        HashSet<Integer> possibles = new HashSet<>();

        while (answer < 10) {
            if (possibles.contains(number))
                break;
            
            // add DP
            HashSet<Integer> p = new HashSet<>();
            
            if (answer == 0) {
                p.add(N);
            } else {
                p.add(Integer.parseInt(String.valueOf(N).repeat(answer + 1)));
                
                // let's combinate DP(1) to DP(N-1)
                int cur = answer + 1;
                for (int i = cur - 1; i >= 1; i--) {
                    HashSet<Integer> DP1 = DP.get(i - 1);
                    HashSet<Integer> DP2 = DP.get(cur - i - 1);
                    for (Integer v1: DP1) {
                        for (Integer v2: DP2) {
                            p.add(v1 * v2);
                            if (v2 != 0)
                                p.add(v1 / v2);
                            p.add(v1 + v2);
                            p.add(v1 - v2);
                        }
                    }
                }
            }
            
            DP.add(p);
            possibles.addAll(p);
            answer++;
        }
        
        if (answer >= 8)
            return -1;
        return answer;
    }
}