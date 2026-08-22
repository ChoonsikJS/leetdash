import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileInputStream;
class Solution
{
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		for(int test_case = 1; test_case <= 10; test_case++)
		{
           List<Integer> boxes = new ArrayList<>();
           int dump = sc.nextInt();
            for (int i = 0; i < 100; i++) {
                boxes.add(sc.nextInt());
            }
            for (int i = 0; i < dump; i++) {
            boxes.sort(Integer::compareTo);
            int[] boxArray = boxes.stream().mapToInt(Integer::intValue).toArray();
                boxArray[0]++;
                boxArray[99]--;
                boxes.clear();
                for (int j = 0; j < 100; j++) {
                    boxes.add(boxArray[j]);
                }
            }
            boxes.sort(Integer::compareTo);
            int[] boxArray = boxes.stream().mapToInt(Integer::intValue).toArray();
            int result = boxArray[99] - boxArray[0];
            System.out.println("#" + test_case + " " + result);
        }
    }
}