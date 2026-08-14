import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.StringTokenizer;

class Solution {
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
			this.r = r;
			this.c = c;
		}
        
        @Override
        public String toString() {
        	return "t:" + t + "r:"+r+" "+"c: "+c;
        }
	}

	static final int[] ROWS = { 0, 0, 1, -1 };
	static final int[] COLS = { 1, -1, 0, 0 };
	static int N;
	static int M;
	static char[][] map;

	static ArrayList<TPos> devil;
	static TPos suyeon;
	static TPos queen;

	static LinkedList<TPos> devilq;
	static LinkedList<TPos> suyeonq;

	static boolean gameEnd;

	static int answer;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int test_case = 1; test_case <= T; test_case++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken(" "));
			M = Integer.parseInt(st.nextToken(" "));

			map = new char[N][M];
            devil = new ArrayList<>();
			setMap(br);

			devilq = new LinkedList<>();
			suyeonq = new LinkedList<>();

			boolean[][] suyeonV = new boolean[N][M];
			boolean[][] devilV = new boolean[N][M];

			answer = -1;

			for (int i =0; i < devil.size(); i++)
				devilq.add(devil.get(i));
			suyeonq.add(suyeon);
            
			gameEnd = false;
			int t = 0;
			while (!gameEnd && !suyeonq.isEmpty()) {
				while (suyeonq.peekFirst().t == t) {
					TPos s = suyeonq.pollFirst();

                    suyeonV[s.r][s.c] = true;
					for (int i = 0; i < 4; i++) {
						if (s.r + ROWS[i] >= 0 && s.r + ROWS[i] < N &&
								s.c + COLS[i] >= 0 && s.c + COLS[i] < M &&
								map[s.r + ROWS[i]][s.c + COLS[i]] != 'X' &&
                           		!suyeonV[s.r + ROWS[i]][s.c + COLS[i]]
                           ) {
							suyeonV[s.r + ROWS[i]][s.c + COLS[i]] = true;
							suyeonq.addLast(new TPos(t + 1, s.r + ROWS[i], s.c + COLS[i]));
						}
					}
				}

				while (!devilq.isEmpty() && devilq.peekFirst().t == t) {
					TPos d = devilq.pollFirst();
					devilV[d.r][d.c] = true;
					for (int i = 0; i < 4; i++) {
						if (d.r + ROWS[i] >= 0 && d.r + ROWS[i] < N &&
								d.c + COLS[i] >= 0 && d.c + COLS[i] < M &&
								map[d.r + ROWS[i]][d.c + COLS[i]] != 'X' &&
								map[d.r + ROWS[i]][d.c + COLS[i]] != 'D' &&
								!devilV[d.r + ROWS[i]][d.c + COLS[i]]                           
                           ) {
							devilV[d.r + ROWS[i]][d.c + COLS[i]] = true;
							devilq.addLast(new TPos(t + 1, d.r + ROWS[i], d.c + COLS[i]));
						}
					}
				}

                int c = suyeonq.size();
				for (int i = 0; i < c; i++) {
					TPos s = suyeonq.pollFirst();

					if (devilV[s.r][s.c]) {
						continue;
					}

					if (queen.r == s.r && queen.c == s.c) {
						gameEnd = true;
						answer = s.t;
						break;
					}
					suyeonq.add(s);
				}
                t++;
			}

			if (answer != -1)
				System.out.printf("#%d %d\n", test_case, answer);
			else
				System.out.printf("#%d GAME OVER\n", test_case);
		}
	}

	private static void setMap(BufferedReader br) throws Exception {
		for (int i = 0; i < N; i++) {
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
					devil.add(new TPos(0, i, j));
				}
			}
		}
	}
}
