import java.util.Scanner;

public class Solution {
	
	static int N;
	static int[][] arr;
	static boolean[] used;
	static int max;
	
	public static void main(String args[]) throws Exception
	{
		
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
		
		for(int test_case = 1; test_case <= T; test_case++)
		{
			 N = sc.nextInt();
			int M = sc.nextInt();
			
			arr = new int[N][N];
			used = new boolean[N];
			max =0;
			
			
			for (int i = 0; i < M; i++) {
				int a=sc.nextInt();
				int b=sc.nextInt();
				arr[a-1][b-1]=arr[b-1][a-1]=1;
			}
			
			
			for(int i=0; i<N;i++) {
				used[i]=true;
				dfs(i,1);
				used[i] =false;
			}
			System.out.println("#"+test_case+" "+ max);
		}
	}
	
	static void dfs(int current, int depth) {
		max= Math.max(max,depth);
		
		for(int next = 0; next<N; next++) {
			
		if(arr[current][next]==1 && !used[next]) {
		
		used[next]=true;
		dfs(next, depth+1);
		used[next]=false;
		}
		}
	}
}
