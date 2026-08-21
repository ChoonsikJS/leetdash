import java.util.Scanner;

public class Solution {

	static long A;
	static long B;
	static long ans;
	
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			
			ans=0;
			A = sc.nextLong();
			B=sc.nextLong();
			
			ans= (A*A) / (B*B);
			
			System.out.println("#"+test_case+" " + ans);
		}
	}


}
