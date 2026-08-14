package submissions.anfdml.swea.2805;

import java.util.Scanner;



public class Solution {
    public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
		for(int test_case = 1; test_case <= T; test_case++)
		{
			int N = sc.nextInt();
			char[][] arr = new char[N][N];
			
			for (int i = 0; i < N; i++) {
				String a = "";
				a = sc.next();
				for (int j = 0; j < N; j++) {
					arr[i][j] = a.charAt(j);
				}
			}
			//전체를 다 합산하고
			int total = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					 total += arr[i][j]-'0';
				}
			}
			//전체에서 (N-1)/2으로 각 모서리 계단 형식으로 빼기  
			//숫자들 계산할때 char로 받았으니 계산할때 -'0' 해서 원래 숫자 값으로 변환 
			int del = 0;
			for (int i = 0; i < N/2 ; i++) {
				for (int j = 0; j <N/2-i; j++) {
					del += arr[i][j]-'0';
				}
			}//왼쪽위
			for (int i = 0; i < N/2 ; i++) {
				for (int j = N/2+1+i; j <N; j++) {
					del += arr[i][j]-'0';
				}
			}//오른쪽 위 
			for (int i = N/2 + 1; i < N ; i++) {
				for (int j = 0; j <i - N/2; j++) {
					del += arr[i][j]-'0';
				}
			}//왼쪽아래
			for (int i = N/2 + 1; i < N ; i++) {
				for (int j = N-(i-N/2); j < N; j++) {
					del += arr[i][j]-'0';
				}
			}//오른쪽아래
			System.out.println("#"+test_case+" "+ (total-del));
		}
	}
		
	
}
 

