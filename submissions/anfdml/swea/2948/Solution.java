import java.util.HashSet;
import java.util.Scanner;

public class Solution {
	static int N;
	static int M;
	static String[] arr;
	static String[] arr1;
	static int count;
	
	public static void main(String args[]) throws Exception
	{
		
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
		
		for(int test_case = 1; test_case <= T; test_case++) {
			N = sc.nextInt();
			M = sc.nextInt();
			count =0;
			HashSet<String> set = new HashSet<>();
			for (int i = 0; i < N; i++) {
				set.add(sc.next());
			}
			
			for (int i = 0; i < M; i++) {
				String str= sc.next();
				if(set.contains(str)) {
					count++;
				}
			}
		
			
			System.out.println("#"+test_case+" " + count);

		}
	}
}