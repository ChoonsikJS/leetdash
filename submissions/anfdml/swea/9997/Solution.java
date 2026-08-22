import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			int N = sc.nextInt();
			int h = 0;
			int m = 0; 
			if(N<30 && N>0) {
				m=N*2;
			}else if(N==0) {
				h=0;
				m=0;
			}
			else {
				h=N/30;
				m=(N%30)*2;
			}
			System.out.println("#"+test_case+" "+h+" "+m);
		}
	}
}
		//360을 12로 나누면 30도 단위로 시간 그 30도의 1도당 2분