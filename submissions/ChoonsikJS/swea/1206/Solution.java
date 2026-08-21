import java.util.Scanner;
import java.io.FileInputStream;
class Solution
{
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		for(int test_case = 1; test_case <= 10; test_case++)
		{
            int N = sc.nextInt();
            int[] building = new int[N];
            for (int i = 0; i < N; i++) {
                building[i] = sc.nextInt();
            }
            int view = 0;
            for (int i = 2; i < N - 2; i++) {
                int left = Math.max(building[i - 2], building[i - 1]);
                int right = Math.max(building[i + 1], building[i + 2]);
                int max = Math.max(left, right);
                if (building[i] > max) {
                    view += building[i] - max;
                }
            }
            System.out.println("#" + test_case + " " + view);
        }
    }
}