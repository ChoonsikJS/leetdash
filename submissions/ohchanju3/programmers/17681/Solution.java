/*
- 지도는 nxn 배열
- 지도 각 칸은 공백 or 벽(#)
- 지도 1, 2 모두 공백이면 공백, 하나라도 벽이면 벽
- 암호화 배열은 지도의 각 가로줄에서 벽을 1, 공백을 0으로 이진수 
- 원래 지도 해독해서 #, 공백으로 구성된 문자열 배열 출력 

문제 풀이 구상 
- 각 arr의 숫자를 이진수로 바꾸기
- map 배열 정의 map[arr.length][arr1.length]
- arr의 배열 크기 만큼 반복
    - arr1과 arr2의 i번째 이진수 비교하기
    - arr1의 이진수 길이만큼 반복
        - arr1과 arr2의 charAt을 해서 j번째가 둘 다 0이면 " ", 둘 중 하나라도 1이면 # -> map 배열 i번째에 담기 ! 
- map 배열 출력 
*/
import java.util.*;

class Solution {
    public String[] solution(int n, int[] arr1, int[] arr2) {
        String[] map = new String[n];
        String[] arr1S = new String[n];
        String[] arr2S = new String[n];
        Arrays.fill(map,"");
       
        //이진수로 바꾸기 
        for(int i=0; i<arr1.length; i++){
            arr1S[i] = Integer.toBinaryString(arr1[i]);
            arr2S[i] = Integer.toBinaryString(arr2[i]);
            arr1S[i] = String.format("%0" + n +"d", Long.parseLong(arr1S[i]));
            arr2S[i] = String.format("%0" + n +"d", Long.parseLong(arr2S[i]));
        }
        
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(arr1S[i].charAt(j) == '0' &&  arr2S[i].charAt(j) == '0') map[i]+=" ";
                else map[i]+="#";
            }
        }
        return map;
    }
}