import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			int N = sc.nextInt();
			int K = sc.nextInt();
			int arr[] = new int[N];
			for (int i = 0; i < N; i++) {
				arr[i] = sc.nextInt();
			}
			int sum = 0;
			for (int i = 0; i < K; i++) {
				int max=0;
				for (int j = 0; j < N; j++) {
					if(arr[j]>max) {
						max = arr[j];
					}
				}
				for (int j = 0; j < N; j++) {
					if(arr[j]==max) {
						sum += max;
						arr[j]=0;
						break;
					}
				}
			}
			System.out.println("#"+test_case+" "+sum);
			
		}
	}
}
		