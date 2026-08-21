import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			int arr[] = new int[10];
			
			for (int i = 0; i < 10; i++) {
				String num = sc.next();
				int sum = 0;
				for (int j = 0; j < num.length(); j++) {
					sum += num.charAt(j)-'0';
				}
				arr[i] = sum;
			}
			int max = 0;
			int min = 55;
			for (int i = 0; i < arr.length; i++) {
				if(arr[i]>max) {
					max = arr[i];
				}
				if(arr[i]<min) {
					min = arr[i];
				}
			}
			System.out.println("#" + test_case + " " + max + " " + min);
		}
	}
}