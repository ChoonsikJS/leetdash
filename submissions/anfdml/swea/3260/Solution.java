import java.math.BigInteger;
import java.util.Scanner;

public class Solution {

	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T= sc.nextInt();
		
		for(int test_case=1; test_case<=T;test_case++) {
			
			BigInteger A = sc.nextBigInteger();
			BigInteger B = sc.nextBigInteger();
			
			
			System.out.println("#"+test_case+" "+ A.add(B));
		}
	}

}
