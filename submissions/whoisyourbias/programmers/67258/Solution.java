import java.util.*;

class Solution {
    public int[] solution(String[] gems) {
        int[] answer = {};
        
        HashSet<String> names = new HashSet<>();
        
        for (String g: gems) {
            names.add(g);
        }
        
        int count = names.size();
        
        int l = 0;
        int r = l;
        names = new HashSet<>();
        TreeMap<Integer, ArrayList<Integer[]>> anlLst = new TreeMap<>();

        while (r < gems.length) {
            names.add(gems[r]);
            // 넣었는데 보석 전부 커버?
            if (names.size() == count) {
                // reset;
                anlLst.putIfAbsent(r-l, new ArrayList<>());
                Integer[] a = new Integer[2];
                a[0] = l;
                a[1] = r;
                anlLst.get(r-l).add(a);
                names.clear();
                r++;
                l = r;
                continue;
            } else {
                r++;
            }
        }
        
        ArrayList<Integer[]> ls = anlLst.pollFirstEntry().getValue();
        Collections.sort(ls, new Comparator<Integer[]>() {
            
            @Override
            public int compare(Integer[] a, Integer[] b) {
                return a[0] - b[0];
            }
        });
        int[] answ = new int[2];
        answ[0] = ls.get(0)[0] + 1;
        answ[1] = ls.get(0)[1] + 1;
        return answ;
    }
}