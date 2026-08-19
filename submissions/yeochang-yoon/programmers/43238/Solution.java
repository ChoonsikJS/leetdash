import java.util.*;

class Solution {
    
    private static int n;
    private static int[] times;
    
    public long solution(int n, int[] times) {
        
        this.n = n;
        this.times = times;
        
        Arrays.sort(times);
        long maxTime = (long) n * times[0];
        long minTime = 0;
        
        
        
        long answer = search(minTime, maxTime);
        return answer;
    }
    
    public long search(long minTime, long maxTime){
        if(minTime == maxTime){
            return minTime;
        }else{
            long midTime = (minTime + maxTime) / 2;
            long sum = 0;
            for(int i = 0; i < times.length; i++){
                sum += (midTime / times[i]);
            }
            
            if(sum < n){
                return search(midTime + 1, maxTime);
            }else{
                return search(minTime, midTime);
            }
        }
    }
}