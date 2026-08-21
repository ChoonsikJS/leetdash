import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			int N = sc.nextInt();
			System.out.print("#"+test_case+" ");
			if(N%2==0) {
				System.out.println("Alice");
			}else {
				System.out.println("Bob");
			}
		}
	}
}
		