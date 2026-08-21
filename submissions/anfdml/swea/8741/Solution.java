import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			
			System.out.print("#"+test_case+" ");
			for (int i = 0; i < 3; i++) {
				String a = sc.next();
				a= a.toUpperCase();
				System.out.print(a.charAt(0));
			}
			System.out.println();
		}
	}
}
		