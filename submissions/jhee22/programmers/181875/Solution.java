class Solution {
    public String[] solution(String[] strArr) {
        String[] answer = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
           // 홀/짝에 따라 
            boolean isEven = i % 2 == 0;
            answer[i] = isEven ? strArr[i].toLowerCase() : strArr[i].toUpperCase(); 
        }
        return answer;
    }
}