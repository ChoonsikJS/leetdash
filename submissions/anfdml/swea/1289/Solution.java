import java.util.Scanner;

public class Solution {
	public static void main(String args[]) throws Exception
	{
		
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
		
		for(int test_case = 1; test_case <= T; test_case++)
		{//원재 영재반 출신인듯 
			String A = sc.next();
			char[] make = new char[A.length()];
			int count = 0;
			char[] arr = new char[A.length()];
			for(int i=0; i< A.length();i++) {
				arr[i]=A.charAt(i);
				make[i] = '0';
			}
			for(int i = 0; i< A.length(); i++) {
				if(arr[i]!=make[i]) {
					char change = make[i];
					for (int j = i; j < A.length(); j++) {
						if(change=='0') {
							make[j]='1';		
						}else {
							make[j]='0';	
						}
					}
					count++;
				}
			}
			System.out.println("#" + test_case + " " + count);	
		}
	}
}