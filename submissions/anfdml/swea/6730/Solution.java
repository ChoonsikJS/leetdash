import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			
			int max = 0;
			int min = 0;
			int N = sc.nextInt();
			int[] arr = new int[N];
			for (int i = 0; i < N; i++) {
				arr[i]=sc.nextInt();
			}
			for (int j = 0; j < N-1; j++) {
				if(arr[j]>arr[j+1]) {
					int a=arr[j]-arr[j+1];
					if(a>min) {
						min= a;
					}
				}else if(arr[j]<arr[j+1]) {
					int b= arr[j+1]-arr[j];
					if(b>max) {
						max=b;
					}
				}
			}
			System.out.println("#"+test_case+" "+ max+" "+min);
			
			
		}
	}

}
