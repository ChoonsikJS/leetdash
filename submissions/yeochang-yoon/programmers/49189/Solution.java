import java.util.*;

class Solution {
    public int solution(int n, int[][] edge) {

        Map<Integer, List<Integer>> map = new HashMap<>();
        // 간선이 양방향이므로 다 넣어줌
        for(int i = 0; i < edge.length; i++){
            map.putIfAbsent(edge[i][0], new ArrayList<Integer>());
            map.putIfAbsent(edge[i][1], new ArrayList<Integer>());
            map.get(edge[i][0]).add(edge[i][1]);
            map.get(edge[i][1]).add(edge[i][0]);
        }

        // 1번에서 전체 노드로 BFS돌려서 각 노드로 가는 최단거리 모두 구함
        Queue<Integer> queue = new ArrayDeque<>();
        int[] visit = new int[n+1];
        Arrays.fill(visit, -1);

        queue.offer(1);
        visit[1] = 0;

        while(!queue.isEmpty()){
            int cur = queue.poll();
            List<Integer> nextList = map.get(cur);

            for(int i = 0; i < nextList.size(); i++){
                if(visit[nextList.get(i)] != -1){
                    continue;
                }
                visit[nextList.get(i)] = visit[cur] + 1;
                queue.offer(nextList.get(i));
            }
        }

        Arrays.sort(visit);
        int max = visit[n];
        int count = 0;

        for(int i = n; i >= 0; i--){
            if(visit[i] == max){
                count++;
            }else{
                break;
            }
        }

        int answer = count;
        return answer;
    }
}