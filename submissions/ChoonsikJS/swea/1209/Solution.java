import java.util.Scanner;
import java.io.FileInputStream;
class Solution
{
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		for(int test_case = 1; test_case <= 10; test_case++)
		{
            int[][] arr = new int[100][100];
            int N = sc.nextInt();
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    arr[i][j] = sc.nextInt();
                }
            }
            int maxSum = Integer.MIN_VALUE;
            for (int i = 0; i < 100; i++) {
                int rowSum = 0;
                int colSum = 0;
                for (int j = 0; j < 100; j++) {
                    rowSum += arr[i][j];
                    colSum += arr[j][i];
                }
                maxSum = Math.max(maxSum, Math.max(rowSum, colSum));
            }
            // Check diagonal sums
            int diag1Sum = 0;
            int diag2Sum = 0;
            for (int i = 0; i < 100; i++) {
                diag1Sum += arr[i][i];
                diag2Sum += arr[i][99 - i];
            }
            maxSum = Math.max(maxSum, Math.max(diag1Sum, diag2Sum));
            System.out.println("#" + test_case + " " + maxSum);
        }
    }
}