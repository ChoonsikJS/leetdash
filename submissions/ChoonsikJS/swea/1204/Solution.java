import java.util.Scanner;
import java.io.FileInputStream;

class Solution
{
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++){
            int N = sc.nextInt();
            int[] scores = new int[101];
            for (int i = 0; i < 1000; i++) {
                scores[sc.nextInt()]++;
            }
            int max = 0;
            for (int i = 0; i <= 100; i++) {
                if (scores[i] > scores[max]) {
                    max = i;
                }
                if (scores[i] == scores[max] && i > max) {
                    max = i;
                }
            }
            System.out.println("#" + test_case + " " + max);
        }
    }
}