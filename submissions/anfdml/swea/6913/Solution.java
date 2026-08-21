import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			int N = sc.nextInt();
			int M = sc.nextInt();
			int max = 0;
			int dlfemd = 0;
			int arr[] = new int[M];
			for (int i = 0; i < N; i++) {
				
				for (int j = 0; j < M; j++) {
					arr[i] += sc.nextInt();
				}
				if(arr[i]>max) {
					max = arr[i];
				}
			}
			for (int i = 0; i < arr.length; i++) {
				if(max == arr[i]) {
					dlfemd++;
				}
			}
			
			System.out.println("#"+test_case+" "+dlfemd+" " + max);
		}
	}
}