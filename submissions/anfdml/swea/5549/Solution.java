import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			 String str = sc.next();
			
			System.out.print("#"+test_case+" ");
			
			int a=(str.charAt(str.length()-1))-'0';
			
			if(a%2==0) {
				System.out.print("Even");
			}else {
				System.out.print("Odd");
			}
			System.out.println();
		}
	}
}