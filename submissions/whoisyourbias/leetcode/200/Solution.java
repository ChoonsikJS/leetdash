import java.util.*;

class Solution {
    static final int[] ROWS = {0,0,1,-1};
    static final int[] COLS = {-1,1,0,0};
    static boolean[][] visited;
    static int cnt;
    static ArrayDeque<BFS> q;

    class BFS {
        int i;
        int j;
        BFS(int i, int j) {this.i = i; this.j = j;}
    }

    public int numIslands(char[][] grid) {
        
        // bfs
        visited = new boolean[grid.length][grid[0].length];
        cnt = 0;

        q = new ArrayDeque<>();
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0 ; j  < grid[0].length; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    q.add(new BFS(i, j));                        
                    while (!q.isEmpty()) {
                        BFS b = q.pollFirst();
                        bfs(b.i, b.j, grid);
                    }
                    cnt++;
                }
                    
            }
        }
        return cnt;
    }

    private void bfs(int i, int j, char[][] grid) {
        // System.out.println(i + " " + j);
        if (visited[i][j])
            return;

        visited[i][j] = true;

        if (grid[i][j] == '0')
            return;

        for (int v = 0; v < 4; v++) {
            int nextI = i + ROWS[v];
            int nextJ = j + COLS[v];

            if (nextI < 0 || nextJ < 0 || nextI >= grid.length || nextJ >= grid[0].length)
                continue;

            if (visited[nextI][nextJ])
                continue;
            
            if (grid[nextI][nextJ] == '0')
                continue;
            
            q.add(new BFS(nextI, nextJ));
        }
    }
}