import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for(int test_case = 1; test_case <= T; test_case++) {
            int[][] arr = new int[301][301];
            int p= sc.nextInt();
            int q= sc.nextInt();
            int num = 1;
            int a=0;
            int b=0;
            int x=0;
            int y=0;

             for (int numx=2; numx<=600;numx++){
                for(int i=1;i<numx;i++){
                    int j = numx-i;
                        if(j>300||i>300){
                            continue;
                        }
                            arr[i][j] = num;
                            if(num==p){
                                 y = j;
                                 x = i;
                            }
                            if(num==q){
                                 b = j;
                                 a = i;
                            
                        }
                        num++;
                   }
                }
            

            System.out.println("#" + test_case + " " + arr[x+a][y+b]);
            
        }
    }
}
