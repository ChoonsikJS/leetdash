import java.util.LinkedList;
import java.util.Queue;

class Solution {
    
    static int [] dx = {-1,1,0,0};
    static int [] dy = {0,0,-1,1};
    static int n;
    static int m;

    static boolean [][] b;
    static Queue<int[]> q;

    int bfs(int[][] maps) {

        while(!q.isEmpty()) {
            
            int[] cur = q.poll();

            int x = cur[0];
            int y = cur[1];
            int dis = cur[2];

            if(x == n - 1 && y == m - 1)
                return dis;

            for(int a = 0; a < 4; a++) {

                int nx = x + dx[a];
                int ny = y + dy[a];

                if(nx < 0 || nx >= n || ny < 0 || ny >= m)
                    continue;
                if(maps[nx][ny] == 0)
                    continue;
                if(b[nx][ny])
                    continue;

                b[nx][ny] = true;
                q.offer(new int[]{nx , ny , dis + 1});
            }
        }
        return -1;
    }





    public int solution(int[][] maps) {
        int answer = 0;
        
        n = maps.length;
        m = maps[0].length;
        
        b = new boolean [n][m];
        q = new LinkedList<>();

        q.offer(new int[]{0,0,1});
        answer = bfs(maps);
        
                
        return answer;
    }
}