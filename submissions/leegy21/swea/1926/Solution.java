import java.util.Scanner;
import java.io.FileInputStream;
 
class Solution
{
    public static void main(String args[]) throws Exception
    {
        Scanner sc = new Scanner(System.in);
         
        int a = sc.nextInt();
         
        for(int i =1; i<=a; i++){
            String str = String.valueOf(i);
            int clapCount = 0;
                 
            for(int j = 0; j < str.length(); j++){
                char ch = str.charAt(j);
                if(ch == '3' || ch == '6' || ch == '9') {
                    clapCount++;
                }
            }
             
            if(clapCount>0){
                for (int k = 0; k<clapCount; k++){
                    System.out.print("-");
                }
            }else{System.out.print(i);
                 }
             
            System.out.print(" ");
        }
 
        sc.close();
    }
}