class Solution {
    
    public int solution(String name) {
        int answer = 0;
        int n = name.length();
        
        for(int i = 0; i < n; i++) {
            int u = name.charAt(i) - 'A';
            int d = 26 - u;
            
            answer += Math.min(u,d);
        }
        
        int move = n - 1;
        
        for(int i = 0; i < n; i++) {
            int next = i + 1;
            
            while(next < n && name.charAt(next) == 'A') {
                next++;
            }
            
            move = Math.min (
                move,
                i * 2 + n - next
            );
            
            move = Math.min (
                move,
                (n - next) * 2 + i
            );
            
        }
        
        
        
        return answer + move;
    }
}