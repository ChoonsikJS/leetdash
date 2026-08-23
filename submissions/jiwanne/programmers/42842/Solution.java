class Solution {
    public int[] solution(int brown, int yellow) {
        
        int xy = brown + yellow;
        
        for(int i = 1; i <= xy; i++) {
            
            if(xy % i == 0) {
                int a = xy / i;
                
                if(a >= i && (a - 2) * (i - 2) == yellow) {
                    return new int[] {a , i};
                }
            }
        }
        
        
        return new int[0];
    }
}