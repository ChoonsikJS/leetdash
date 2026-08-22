import java.util.Scanner;

public class Solution {
    static int N;
    static int K;
    static int arr[];
    static int count ;
     
    public static void main(String args[]) throws Exception
    {
         
        Scanner sc = new Scanner(System.in);
        int T;
        T=sc.nextInt();
         
        for(int test_case = 1; test_case <= T; test_case++)
        {
                N = sc.nextInt();
                K = sc.nextInt();
            arr = new int[N];
            count = 0;
            for (int i = 0; i < N; i++) {
                arr[i]=sc.nextInt();
            }
            dfs(0,0);   
            System.out.println("#"+test_case+" " + count);
        }
    }
    static void dfs(int index, int sum) {
        if(index == N) {
            if(sum == K) {
                count++;
            }
        return; 
    }
        dfs(index+1,sum+arr[index]);
         
        dfs(index+1, sum);
    }
}