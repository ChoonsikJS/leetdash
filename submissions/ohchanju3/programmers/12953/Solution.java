/*
최소 공배수 = 두수의 곱 / 최대 공약수 
최대 공약수 - 유클리드 호제법 (나머지 중심)
예) 18, 12
18 / 12 = 1 ... 6
12/6 = 2 ... 0
6 / 0 = 0
-> 6
*/

class Solution {
    public int solution(int[] arr) {
        int ans = arr[0];
        for(int i = 1; i<arr.length; i++){
            ans = (ans * arr[i]) / gcd(ans, arr[i]);
        }
        return ans;
    }
    static int gcd(int a, int b){
        while(b!=0){
            int tmp = a%b;
            a = b;
            b = tmp;
        }
        return a;
    }
}