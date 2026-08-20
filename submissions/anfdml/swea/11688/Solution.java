import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			int a=1;
			int b=1;
			String ltRt = sc.next();
			
			for (int i = 0; i < ltRt.length(); i++) {
				if(ltRt.charAt(i)=='L') {
					b=a+b;
				}else {
					a=a+b;
				}
			}
			System.out.println("#"+test_case+" "+a+" "+b);
			
		}
	}

}
