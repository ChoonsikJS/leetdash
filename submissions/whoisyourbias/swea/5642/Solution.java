import java.util.*;
import java.io.*;

class Solution
{
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());

		for(int test_case = 1; test_case <= T; test_case++)
		{
			st = new StringTokenizer(br.readLine());
        	int N = Integer.parseInt(st.nextToken());
            
            int[] arr = new int[N];
            st = new StringTokenizer(br.readLine());
        	
            for (int i = 0 ; i < N; i++) {
            	arr[i] = Integer.parseInt(st.nextToken());
            }
         
            int max = Integer.MIN_VALUE;
            int sum = 0;
            int l = 0;
            while (l < N) {
                sum += arr[l];
                max = Math.max(max, sum);
                
                if (sum < 0) {
                	l++;
                    sum = 0 ;
                } else {
                	l++;
                }
            }
            System.out.printf("#%d %d\n", test_case, max);
		}
	}
}