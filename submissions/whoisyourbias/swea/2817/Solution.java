import java.util.Scanner;

class Solution
{
    static int N;
    static int K;
    static int sum;
    static int[] arr;
    static int count;
    static int selected_cnt;
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++)
		{
			N = sc.nextInt();
            K = sc.nextInt();
            
            selected_cnt = 0;
            count = 0;
            sum = 0;
            arr = new int[12];
            for (int i = 1; i <= 12; i++)
                arr[i -1] = i;
            
            subset(0);
            System.out.printf("#%d %d\n", test_case, count);
		}
	}
    
    private static void subset(int depth) {
        if (selected_cnt == N) {
        	if (sum == K) {
            	count++;
            } 
            return;
        }
        if (depth == 12)
            return;
        
        
        sum += arr[depth];
        selected_cnt++;
        subset(depth + 1);
        sum -= arr[depth];
        selected_cnt--;
        subset(depth + 1);
    }
}
