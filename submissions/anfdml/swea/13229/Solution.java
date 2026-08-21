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
			String[] arr = {"MON","TUE","WED","THU","FRI","SAT","SUN"};
			String we= sc.next();
			int count = 0;
			for (int i = 0; i < arr.length; i++) {
				if(we.equals(arr[i])) {
					if(we.equals("SUN")) {
						count = count + 7 ;
					}else {
						count = 6-i ;
					}
				}
			}
			System.out.println("#"+test_case+" "+count);
			
		}
	}
}
