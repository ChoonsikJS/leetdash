import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			int N = sc.nextInt();
			int M = sc.nextInt();
			int arr[] = new int[N+M+2];
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= M; j++) {
					arr[i+j]++;
				}
			}// 더한 값의 횟수를 행렬에 저장
			int Max = 0;
			for (int i = 0; i < arr.length; i++) {
				if(Max<arr[i]) {
					Max = arr[i];
				}
			}
			System.out.print("#"+test_case);
			for (int i = 0; i < arr.length; i++) {
				if(Max==arr[i]) {
					System.out.print(" "+i);
				}
			}//가장 많은 값의 index를 출력
			System.out.println();
		}
	}

}