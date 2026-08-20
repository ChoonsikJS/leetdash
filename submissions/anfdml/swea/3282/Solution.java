import java.util.ArrayList;
import java.util.Scanner;

public class Solution {

	static int N;
	static int K;
	static ArrayList<Integer> V1;
	static ArrayList<Integer> C1;
	static int max;
	static int cost;
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			
			N=sc.nextInt();
			K=sc.nextInt();
			max = 0;
			V1= new ArrayList<>();
			C1= new ArrayList<>();
			for (int i = 0; i < N; i++) {
				V1.add(sc.nextInt());
				C1.add(sc.nextInt());
			}
			dfs(0,0,0);
			
			System.out.println("#"+test_case+" "+ max);
			
		}
	}
	static void dfs(int index, int vol,int cost) {
		if(index==N) {
		max=Math.max(max, cost);
		return;
		}
		if(vol+V1.get(index)<=K) {
		
			dfs(index+1,vol+V1.get(index),cost+C1.get(index));
			
			
		}
		dfs(index+1,vol,cost);
	}

}
