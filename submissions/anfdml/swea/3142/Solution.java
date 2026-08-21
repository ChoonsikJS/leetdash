import java.util.Scanner;

public class Solution {

	static int N;
	static int M;
	static int twinhorn;
	static int unicon;
	
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			
			 N= sc.nextInt();
			 M= sc.nextInt();
			 unicon = 2*M-N;
			 twinhorn = N-M;
			
			System.out.println("#"+ test_case+ " " + unicon+" "+ twinhorn);
			
			
		}
	}


}
