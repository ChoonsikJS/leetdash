import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String a = sc.next();
        // for-each 순회할 수 있는 자료형은 배열/ 컬렉션 
        // String 을 문자열 배열 String.toCharArray() 로 바꾸면 됨 
        for (char c : a.toCharArray()) {
            System.out.println(c); 
        }
    }
}