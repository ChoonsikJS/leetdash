import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			
			String alpha = sc.next();
			System.out.print("#"+test_case+" ");
			for (int i = 0; i < alpha.length();i++) {
				if(alpha.charAt(i)!='a'&&alpha.charAt(i)!='e'&&alpha.charAt(i)!='i'&&alpha.charAt(i)!='o'&&alpha.charAt(i)!='u') {
					System.out.print(alpha.charAt(i));
				}
				
			}
			System.out.println();
			
		}
	}

}
