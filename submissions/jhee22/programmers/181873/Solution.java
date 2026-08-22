class Solution {
    public String solution(String my_string, String alp) {
        StringBuilder sb = new StringBuilder(); 
        for (char s : my_string.toCharArray()) {
            // 아스키코드 계산으로 직접 구현 : (char) (c - 'a' + 'A'); 
            if (s == alp.charAt(0)) {
                sb.append(Character.toUpperCase(s)); 
            } else {
                sb.append(s); 
            }
        }
        return sb.toString(); 
    }
}