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
			int A = sc.nextInt();
			int B = sc.nextInt();
			
			int count = 0;
			for (int i = A; i <= B; i++) {
				String str = Integer.toString(i);
				boolean palin =true;
				
				for (int j = 0; j < str.length()/2; j++) {
					if(str.charAt(j) != str.charAt(str.length()-1-j)) {
						palin =false;
						break;
					}
				}
				if(palin) {
					int num = (int)Math.sqrt(i);
					
					if (num* num == i) {
						String str1 = Integer.toString(num);
						boolean root = true;
						
						for (int j = 0; j < str1.length()/2; j++) {
							if(str1.charAt(j) != str1.charAt(str1.length()-1-j)) {
								root =false;
								break;
							}
						}
						
						if(root) {
							count++;
						}
						
						
						
					}
					
				}
				
			}
			System.out.println("#"+test_case+" "+count);
		}
	}
}
    