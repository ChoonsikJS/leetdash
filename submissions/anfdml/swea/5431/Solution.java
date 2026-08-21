import java.util.ArrayList;
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
			int N = sc.nextInt();
			int K = sc.nextInt();
			
			int arr[] = new int[N];
			
			for (int i = 0; i < N; i++) {
				arr[i]=i+1;
			}
			int yes[] = new int[K];
			for (int i = 0; i < K; i++) {
				yes[i]=sc.nextInt();
			}
			
			ArrayList<Integer> no = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				boolean nohome = true;
				for (int j = 0; j < K; j++) {
					if(arr[i] == yes[j]) {
						nohome = false;
						break;
					}
				}
				if(nohome) {
					no.add(arr[i]);
				}
			}
			System.out.print("#"+test_case+" ");
			for (int num : no) {
				System.out.print(num+" ");
			}
			System.out.println();
		}
	}
}
