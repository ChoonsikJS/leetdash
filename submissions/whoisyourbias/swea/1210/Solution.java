import java.util.*;

class Solution
{
    static class Pos {
    	int r;
        int c;
        Pos(int r, int c) {
        	this.r = r;
            this.c = c;
        }
        
        @Override
        public String toString() {
        	return this.r + " " + this.c;
        }
    }
    
    static class PosWithStart extends Pos {
        int sr;
        int sc;
        int dir;
        // 0 -> r
    // 1 -> l
    // 2 -> d
    	PosWithStart(int sr, int sc) {
            super(sr, sc);
            this.sr = sr;
            this.sc = sc;
            this.dir = 2;
        }
        PosWithStart(int r, int c, int sr, int sc, int dir) {
            super(r, c);
            this.sr = sr;
            this.sc = sc;
            this.dir = dir;
        }
    }
    
    static final int[] ROWS = {0,0,1,-1};
    static final int[] COLS = {1,-1, 0, 0};
    
    static int[][] map;
    static ArrayDeque<PosWithStart> queue;
    static Pos end;
    static int answer;
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);

		for(int test_case = 1; test_case <= 10; test_case++)
		{
            sc.nextInt();

            answer = -1;
            map = new int[100][100];
            queue = new ArrayDeque<PosWithStart>();
            
            for (int i = 0 ; i < 100; i++) {
            	for (int j = 0 ; j  < 100; j++) {
                	int v= sc.nextInt();
                    map[i][j] = v;
                    // set end pos
                    if (v == 2) {
                    	end = new Pos(i, j);
                        map[i][j] = 1;
                    }
                    // set start points;
                    if (i == 0 && v == 1) {
                    	queue.add(new PosWithStart(i, j));
                    }
                }
            }
            
            
            while (!queue.isEmpty()) {
            	PosWithStart p = queue.pollFirst();
                dfs(p);
            }
            
            System.out.printf("#%d %d\n", test_case, answer);
		}
	}
    
    private static void dfs(PosWithStart p) {
      
        if (answer != -1)
            return;
        if (p.r == end.r && p.c == end.c) {
        
        	answer = p.sc;
            queue.clear();
            return;
        }
        
		// 0 -> r
	    // 1 -> l
	    // 2 -> d
        // left -> c - 1;
		if (p.dir == 2 && p.c - 1 >=0 && map[p.r][p.c - 1] == 1) {
            dfs(new PosWithStart(p.r, p.c - 1, p.sr, p.sc, 1));
        } else if (p.dir == 2 && p.c + 1 < 100 && map[p.r][p.c + 1] == 1) {
            dfs(new PosWithStart(p.r, p.c + 1, p.sr, p.sc, 0));
        } else if ((p.dir == 1 || p.dir == 0) && p.r + 1 < 100 && map[p.r + 1][p.c] == 1) {
            dfs(new PosWithStart(p.r + 1, p.c, p.sr, p.sc, 2));
        } else if (p.r + ROWS[p.dir] >= 0 && p.r + ROWS[p.dir] < 100 &&
                  	p.c + COLS[p.dir] >= 0 && p.c + COLS[p.dir] < 100 &&
                   map[p.r + ROWS[p.dir]][p.c + COLS[p.dir]] == 1
                  )  {
        	dfs(new PosWithStart(p.r + ROWS[p.dir], p.c + COLS[p.dir], p.sr, p.sc, p.dir));
        }
    }
}
