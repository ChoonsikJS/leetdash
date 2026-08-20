import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			
			int N = sc.nextInt();
			int[] arr = new int[N];
			for(int i=0; i<N;i++) {
				arr[i]=sc.nextInt();
			}
			double acount = 0;
			int count =0;
			for (int i = 0; i < N; i++) {
				acount+=arr[i];
			}
			for (int i = 0; i < arr.length; i++) {
				if(acount/N >=arr[i]) {
					count++;
				}
			}
			
			
			System.out.println("#"+test_case+" "+ count);
		}
	}

}
