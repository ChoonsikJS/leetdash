class Solution {
    public String[] solution(String my_string) {
        // trim():
        // split("\\s+") : 
        String[] result = my_string.trim().split("\\s+");
        return result; 
    }
}