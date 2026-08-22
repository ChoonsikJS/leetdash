class Solution {
    public int solution(String myString, String pat) {
        // 소문자로 통일 드가자 
        myString = myString.toLowerCase(); 
        pat = pat.toLowerCase();   
        if (myString.contains(pat)){
            return 1; 
        }
        return 0;
    }
}