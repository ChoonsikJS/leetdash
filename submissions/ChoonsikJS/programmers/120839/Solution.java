class Solution {
    public String solution(String rsp) {
        String answer = "";
        for (char c : rsp.toCharArray()) { // str > char[] 하나씩 순회
            switch (c) {
                case '2':
                    answer += "0";
                    break;
                case '0':
                    answer += "5";
                    break;
                case '5':
                    answer += "2";
                    break;
            }
        }
        return answer;
    }
}