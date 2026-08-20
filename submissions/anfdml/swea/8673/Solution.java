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
			int K = sc.nextInt();
			int[] arr = new int[(int) Math.pow(2, K)];
			
			for (int i = 0; i < arr.length; i++) {
				arr[i] = sc.nextInt();
			}
			int nojam = 0;
			int tonerment = 0;
			while(tonerment < K) {
				int[] next = new int[arr.length/2];
			for (int i = 0; i < arr.length; i+=2) {
				nojam += Math.abs(arr[i]-arr[i+1]);
				next[i/2] = Math.max(arr[i],arr[i+1]);
				}
			arr= next;
			tonerment++;
			}
			System.out.println("#"+test_case+ " " + nojam);
		}
	}
}
