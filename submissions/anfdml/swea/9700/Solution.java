import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			double p = sc.nextDouble();
			double q = sc.nextDouble();
			
			double a = (1-p)*q;
			double b = p*(1-q)*q;
			
			if(a<b) {
				System.out.println("#"+test_case+" "+ "YES");
			}else {
				System.out.println("#"+test_case+" "+ "NO");
			}
		}
	}
}
		