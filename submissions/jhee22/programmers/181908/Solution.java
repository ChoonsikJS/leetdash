class Solution {
    public int solution(String my_string, String is_suffix) {
        int answer = 0; 
        String letter = ""; 
        int idx = 0; 
        while (idx < my_string.length()){
            for (int i = idx; i < my_string.length(); i++) {
                letter += my_string.charAt(i);
            }
            if (letter.length() >= is_suffix.length()) {
                if (letter.equals(is_suffix)) {
                return 1; 
                }          
     
            }
            letter = "";
            idx++; 
        }
        return answer;
    }
}