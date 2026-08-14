import java.util.*;

class Solution {
    public int[] solution(int n, int[][] roads, int[] sources, int destination) {

        Map<Integer, List<Integer>> map = new HashMap<>();
        int[] result = new int[sources.length];

        for(int i = 0; i < roads.length; i++){
            map.putIfAbsent(roads[i][0], new ArrayList<Integer>());
            map.putIfAbsent(roads[i][1], new ArrayList<Integer>());
            map.get(roads[i][0]).add(roads[i][1]);
            map.get(roads[i][1]).add(roads[i][0]);
        }

        for(int i = 0; i < sources.length; i++){
            int[] visit = new int[n+1];
            Arrays.fill(visit, -1);
            Queue<Integer> queue = new ArrayDeque<>();

            queue.offer(sources[i]);
            visit[sources[i]] = 0;

            loop:
            while(!queue.isEmpty()){
                int cur = queue.poll();
                List<Integer> nextList = map.get(cur);

                if(nextList == null){
                    continue;
                }

                for(int j = 0; j < nextList.size(); j++){
                    int next = nextList.get(j);
                    if(visit[next] != -1){
                        continue;
                    }
                    if(next == destination){
                        visit[next] = visit[cur] + 1;
                        result[i] = visit[destination];
                        break loop;
                    }
                    visit[next] = visit[cur] + 1;
                    queue.offer(next);
                }
            }
            if(visit[destination] == -1){
                result[i] = -1;
            }
        }
        return result;
    }
}