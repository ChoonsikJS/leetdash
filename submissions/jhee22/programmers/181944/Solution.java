import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        // 삼항 연산자 활용 
        System.out.println(n + ( n % 2 == 0 ? " is even" : " is odd"));
    }
}