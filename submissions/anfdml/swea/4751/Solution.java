import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			String str= sc.next();
			
			
			for (int i = 0; i < str.length(); i++) {
				System.out.print("..#.");
			}System.out.print(".");
			System.out.println();
			for (int i = 0; i < str.length(); i++) {
				System.out.print(".#.#");
			}System.out.print(".");
			System.out.println();
			for (int i = 0; i < str.length(); i++) {
				System.out.print("#."+str.charAt(i)+".");
			}System.out.print("#");
			System.out.println();
			for (int i = 0; i < str.length(); i++) {
				System.out.print(".#.#");
			}System.out.print(".");
			System.out.println();
			for (int i = 0; i < str.length(); i++) {
				System.out.print("..#.");
			}System.out.print(".");
			System.out.println();
		}
	}
}
		