import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			int N =sc.nextInt();
			int sum = 0;
			int max = 0; 
			for (int i = 0; i < N; i++) {
				int num =sc.nextInt();
				sum += num;
				if(num>max) {
					max=num;
				}
			}
			int ans = sum+max+N;
			System.out.println("#"+test_case+" "+ans);
			
		}
	}
}
		