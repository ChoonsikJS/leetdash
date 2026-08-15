
class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        
        int[] s = new int [speeds.length];
        int[] temp = new int [progresses.length];
        
        for(int i = 0; i < speeds.length; i++) {
            int count = 0;
            
            while(progresses[i] < 100) {
                progresses[i] += speeds[i];
                count++;                
            }
            temp[i] = count;
        }
        int idx = 0;
        s[idx]++;
        
        int max = temp[idx];
        
        for(int i = 1; i < speeds.length; i++) {
            if(temp[i] <= max) {
                s[idx]++;
            } else if(temp[i] > max) {
                max = temp[i];
                idx++;
                s[idx]++;
            }
        }
        
        int [] res = new int[idx + 1];
        for(int i = 0; i < idx + 1; i++) {
            res[i] = s[i];
        }
        
        return res;
        
    }
}