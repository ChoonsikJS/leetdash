import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			String str = sc.next();
			System.out.print("#"+test_case+" ");
			for (int i = str.length()-1; i >= 0; i--) {
				if(str.charAt(i)=='b') {
					System.out.print('d');
				}
				if(str.charAt(i)=='d') {
					System.out.print('b');
				}
				if(str.charAt(i)=='p') {
					System.out.print('q');
				}
				if(str.charAt(i)=='q') {
					System.out.print('p');
				}
			}
			System.out.println();	
		}
	}
}