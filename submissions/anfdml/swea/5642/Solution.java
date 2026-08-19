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
             
            int[] arr = new int[N];
            for (int i=0;i<N;i++) {
                arr[i]= sc.nextInt();
            }
            int Max = arr[0];
            int now = arr[0];
            for (int i = 1; i < N; i++) {
                 
                 
                    now = Math.max(arr[i],now+arr[i]);
                    if(Max<now) {
                        Max=now;
                    }
                     
                }
             
            System.out.println("#"+test_case+ " " +Max);
             
             
             
             
             
        }
    }
}