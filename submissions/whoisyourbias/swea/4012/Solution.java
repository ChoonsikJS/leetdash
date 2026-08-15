import java.util.*;

class Solution
{
    static int[][] foods;
    static int min;
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
        
		for(int test_case = 1; test_case <= T; test_case++)
		{
			int N = sc.nextInt();
            
            foods = new int[N][N];
            boolean[] visited = new boolean[N];
            for (int i = 0 ; i < N; i++) {
            	for (int j = 0; j < N; j++) {
                	foods[i][j] = sc.nextInt();
                }
            }
            
            min = Integer.MAX_VALUE;
            
            comb(0, N / 2, visited, 0);
            
            System.out.printf("#%d %d\n", test_case, min);
		}
	}
    
    private static void comb(int depth, int R, boolean[] visited, int start) {
        if (depth == R) {
            int s1 = 0;
            int s2 = 0;
            for (int i = 0; i < visited.length; i++) {
                for (int j = 0; j < visited.length; j++) {
                    if (visited[i] && visited[j]) {
                    	s1 += foods[i][j];    
                    } else if (!visited[i] && !visited[j]) {
                    	s2 += foods[i][j];
                    }
                }
            }
            
            min = Math.min(min, Math.abs(s2 - s1));
            
            return;
        }

        for (int i = start; i < visited.length; i++) {
            visited[i] = true;
            comb(depth + 1, R, visited, i + 1);
            visited[i] = false;
        }
	}	
}
