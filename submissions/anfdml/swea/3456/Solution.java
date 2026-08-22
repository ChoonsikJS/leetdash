import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			int arr[]=new int[3];
			
			for (int i = 0; i < arr.length; i++) {
				arr[i] = sc.nextInt();
			}
			int count[] = new int[3];
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr.length; j++) {
					if(arr[i]==arr[j]) {
						count[i] += 1;
					}
				}
			}
			int ans = 0; 
			for (int i = 0; i < count.length; i++) {
				if(count[i]%2!=0) {
					ans = arr[i];
				}
			}
			System.out.println("#"+test_case+" "+ ans);
			
		}
	}
}