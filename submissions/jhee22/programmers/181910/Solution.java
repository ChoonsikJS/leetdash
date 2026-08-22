class Solution {
    public String solution(String my_string, int n) {
        // length 가 총 n개, 
        int len = my_string.length(); 
        return my_string.substring(len-n, len);
    }
}