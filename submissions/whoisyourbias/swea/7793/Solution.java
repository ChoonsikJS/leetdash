import java.util.*;
import java.io.*;


class Solution
{
    static class Time {
    	int t;
        Time(int t) {
        	this.t = t;
        }
    }
    
    static class TPos extends Time {
        int r;
        int c;
        TPos(int t, int r, int c) {
        	super(t);
            this.r= r;
            this.c =c;
        }
    }
    
    static final int[] ROWS = {0,0,1,-1};
    static final int[] COLS = {1,-1,0,0};
    static int N;
    static int M;
    static char[][] map;
    
    static TPos devil;
    static TPos suyeon;
    static TPos queen;
    
    static LinkedList<TPos> devilq;
    static LinkedList<TPos> suyeonq;
    
    static boolean gameEnd;
    
	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for(int test_case = 1; test_case <= T; test_case++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken(" "));
            M = Integer.parseInt(st.nextToken(" "));
            
            map = new char[N][M];
            setMap(br);
            
            devilq = new LinkedList<>();
            suyeonq = new LinkedList<>();
    
			boolean[][] suyeonV = new boolean[N][M];
			boolean[][] devilV = new boolean[N][M];

            for (int i = 0; i < N; i++) {
            	System.out.println(map[i]);
            }
            
            devilq.add(devil);
            suyeonq.add(suyeon);
            
            gameEnd = false;
            int t = 0;
			while (!gameEnd) {
            	while (suyeonq.peekFirst().t == t) {
					TPos s = suyeonq.pollFirst();

					// 방문처리?
					suyeonV[s.r][s.c] = true;
					for (int i = 0; i < 4; i++) {
						if (s.r + ROWS[i] >= 0 && s.r + ROWS[i] < N &&
							s.c + COLS[i] >= 0 && s.c + COLS[i] < M &&
							map[s.r + ROWS[i]][s.c + COLS[i]] != 'X') {

							if (suyeonV[s.r + ROWS[i]][s.c + COLS[i]])
								continue;
							suyeonq.addLast(new TPos(t + 1, s.r + ROWS[i], s.c + COLS[i]));
						}
					}
				}

				while (devilq.peekFirst().t == t) {
					TPos d = devilq.pollFirst();

					devilV[s.r][s.c] = true;
				}
			}
		}
	}
    
    private static void setMap(BufferedReader br) throws Exception {
    	for (int i = 0 ; i < N; i++) {
            String l = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = l.charAt(j);
                if (l.charAt(j) == 'S') {
                	suyeon = new TPos(0, i, j);
                }
                if (l.charAt(j) == 'D') {
                	queen = new TPos(0, i, j);
                }
                if (l.charAt(j) == '*') {
                	devil = new TPos(0,i,j);
                }
            }
        }
    }
}
