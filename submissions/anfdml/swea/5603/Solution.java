import java.util.Scanner;


class Solution
{
	public static void main(String args[]) throws Exception
	{
		
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
		
		for(int test_case = 1; test_case <= T; test_case++)
		{
		//규호는 상식이라는게 없나? 
			
			int N = sc.nextInt();
			int [] arr = new int [N];
			
			for (int i = 0; i < N; i++) {
				arr[i] = sc.nextInt();
			}
			int sum = 0;
			for (int i = 0; i <N; i++) {
				sum+=arr[i];
			}
			
			int avg=sum/N;
			
			int count = 0;
			for (int i = 0; i < N; i++) {
				count +=Math.abs(arr[i]-avg);
			}
			System.out.println("#"+test_case+" "+ count/2);
		
			
			
			
			
		}
	}
}