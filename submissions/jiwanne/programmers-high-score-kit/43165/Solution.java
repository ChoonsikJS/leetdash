
class Solution {
    
private int sum;

   public int dfs(int x, int[] numbers, int target) {
        int count = 0;

        if(x == numbers.length) {
            if(sum == target) {
                return 1;
            } else {
                return 0;
            }
        }        

        sum += numbers[x];
        count += dfs(x + 1, numbers, target);


        sum -= numbers[x];
        sum -= numbers[x];
        count += dfs(x + 1, numbers, target);
        
        sum += numbers[x];

        

        return count;
   } 
    
    
    public int solution(int[] numbers, int target) {
        sum = 0;
        int answer = 0;
        answer = dfs(0,numbers,target);
        return answer;
    }
}