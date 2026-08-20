import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			int A = sc.nextInt();
			int B = sc.nextInt();
			
			if(A+B<24) {
				System.out.println("#"+test_case+" "+(A+B));
			}else if(A+B==24) {
				System.out.println("#"+test_case+" "+0);
			}else {
				System.out.println("#"+test_case+" "+((A+B)-24));
			}
			
		}
	}

}
