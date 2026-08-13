class Solution {
    public String solution(int age) {
        String answer = "";
        String[] arr = {"a","b","c","d","e","f","g","h","i","j"};
        if(age == 1000) {
            answer += arr[age/1000]; // 1000의 자리
            answer += arr[(age%1000)/100];// 100의 자리
            answer += arr[(age%100)/10];// 10의 자리
            answer += arr[age%10];
        } else if(age >= 100) {
            answer += arr[age/100];
            answer += arr[(age%100)/10];
            answer += arr[age%10];
        }else if(age <10) {
            answer += arr[age%10];
        } else {
            answer += arr[age/10]; // 10의 자리
            answer += arr[age%10];  
        }
        return answer;
    }
}