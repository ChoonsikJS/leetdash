import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			int arr[] = new int[5];
			int ans = 0;
			for (int i = 0; i < arr.length; i++) {
				arr[i]=sc.nextInt();
				if(arr[i]<40) {
					ans += 40;
				}else {
					ans += arr[i];
				}
			}
			System.out.println("#"+test_case+" "+ (ans/5));
		}
	}
}