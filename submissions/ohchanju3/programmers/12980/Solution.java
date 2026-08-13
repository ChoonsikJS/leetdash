/*
[문제 분석]
- 한 번에 +k 점프 or 현재까지 온 거리 x 2 (순간이동)
- 순간이동 -> 건전지 사용량 줄지 않음
- k칸 점프 -> k만큼의 건전지 사용량 소모
점프 이동 최솟값 return 

[알고리즘 구상]
- 배터리 카운트 변수 정의
- 거꾸로 생각해보기. n까지 오려면 어떤 과정이 필요했는가 ??
- n이 0보다 클 때까지 반복
    - 짝수면 -> 2로 나누기 계속 
    - 홀수면 -> n-1하고 배터리 카운트 ++

*/
import java.util.*;

public class Solution {
    public int solution(int n) {
        int battery = 0;
        
        while (n>0){
            if(n%2==0) n/=2;
            else {
                n--;
                battery++;
            }
        }
        
        return battery;
        }
    }