

class Solution {
    boolean solution(String s) {
        boolean answer = true;
        
        int balance = 0;
        
        char [] c = new char [s.length()];
        for(int i = 0; i < s.length(); i++) {
            c[i] = s.charAt(i);
        }
        
        for(int i = 0; i < s.length(); i++) {
            if(c[i] == '(') {
                balance++;
            } else {
                balance--;
            }
            if(balance < 0)
                answer = false;
            
        }
        if(balance != 0)
            answer = false;
        
       
       
        return answer;
    }
}