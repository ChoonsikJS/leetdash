import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			int N = sc.nextInt();
			String spell = sc.next();
			String shit = sc.next();
			int count =0;
			for (int i = 0; i < N; i++) {
				if(spell.charAt(i)==shit.charAt(i)) {
					count++;
				}
			}
			System.out.println("#"+test_case+" "+count);
		}
	}

}
