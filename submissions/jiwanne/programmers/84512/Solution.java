import java.util.List;
import java.util.ArrayList;

class Solution {
    
    static List<String> list;
    static char [] c = {'A', 'E', 'I', 'O', 'U'};
    
    void dfs (String word , int x ) {
        if(x > 5)
            return;
        if(!list.equals("")) {
            list.add(word);
        }
        for(int i = 0; i < 5; i++) {
            dfs(word + c[i] , x + 1);            
        }
    }
    
    
    public int solution(String word) {
        
        list = new ArrayList<>();
        dfs("" , 0);
        
        return list.indexOf(word);
    }
}