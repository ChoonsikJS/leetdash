import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			String num = sc.next();
			int count =0;
			boolean[] use= new boolean[10];
			for(int i=0;i<num.length();i++) {
				
				int nums=num.charAt(i)-'0';
				if(!use[nums]) {
					use[nums]=true;
					count++;
				}
			}
			System.out.println("#"+test_case+" "+ count);
		}
	}

}
