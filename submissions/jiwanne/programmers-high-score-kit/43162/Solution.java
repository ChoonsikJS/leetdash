class Solution {



    public void dfs (int x , int[][] computers, boolean[] arr) {

        arr[x] = true;

        for(int i = 0; i < computers.length; i++) {
            
            if(computers[x][i] == 1 && !arr[i]) {
                dfs(i , computers, arr);
            }
        }
    }



    public int solution(int n, int[][] computers) {
        
        int answer = 0;
        
        boolean[] arr = new boolean[n];

        for(int i = 0; i < n; i++) {
            if(!arr[i]) {
                dfs(i, computers, arr);
                answer++;
            }
        }
        
        
        
        
        
        
        return answer;
    }

}