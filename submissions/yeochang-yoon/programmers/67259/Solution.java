import java.util.*;

class Solution {
    public int solution(int[][] board) {
        
        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};
        
        int[][][] visit = new int[board.length][board[0].length][2];
        
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] {0, 0, 0});
        queue.offer(new int[] {0, 0, 1});
        
        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            int r = cur[0];
            int c = cur[1];
            int axis = cur[2];
            
            for(int i = 0; i < 4; i++){
                int nr = r + dr[i];
                int nc = c + dc[i];
                
                if(nr < 0 || nr >= board.length || nc < 0 || nc >= board[0].length){
                    continue;
                }
                if(board[nr][nc] == 1){
                    continue;
                }
                
                if(i < 2){
                    if(axis == 0){
                        int cost = visit[r][c][axis] + 100 + 500;
                        if(visit[nr][nc][1] == 0 || visit[nr][nc][1] > cost){
                            visit[nr][nc][1] = cost;
                            queue.offer(new int[] {nr, nc, 1});
                        }
                    }else{
                        int cost = visit[r][c][axis] + 100;
                        if(visit[nr][nc][1] == 0 || visit[nr][nc][1] > cost){
                            visit[nr][nc][1] = cost;
                            queue.offer(new int[] {nr, nc, 1});
                        }
                    }
                }else{
                    if(axis == 0){
                        int cost = visit[r][c][axis] + 100;
                        if(visit[nr][nc][0] == 0 || visit[nr][nc][0] > cost){
                            visit[nr][nc][0] = cost;
                            queue.offer(new int[] {nr, nc, 0});
                        }
                    }else{
                        int cost = visit[r][c][axis] + 100 + 500;
                        if(visit[nr][nc][0] == 0 ||visit[nr][nc][0] > cost){
                            visit[nr][nc][0] = cost;
                            queue.offer(new int[] {nr, nc, 0});
                        }
                    }
                }
            }
        }//while
        int n = board.length-1;
        if(visit[n][n][0] == 0){
            return visit[n][n][1];
        }
        if(visit[n][n][1] == 0){
            return visit[n][n][0];
        }
        int min = Math.min(visit[n][n][0], visit[n][n][1]);
        
        int answer = min;
        return answer;
    }
}