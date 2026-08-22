// 가위 2, 바위 0 보 5
class Solution {
    public String solution(String rsp) {
        StringBuilder sb = new StringBuilder(); 
        // 별도로 char[] 만드는거 보다 그냥 idx 로 접근해서 charAt(i) 하시긔 
        for (char s : rsp.toCharArray()) {
            if (s =='2'){
                sb.append('0');
            } else if (s == '0') {
                sb.append('5');
            } else {
                sb.append('2');
            }
        }
        return sb.toString();
    }
}