class Solution {
    // 현재 탐색하고 있는 numbers 배열의 인덱스, 현재 실행 결과 
    int idx = 0; 
    int curr = 0; 
    int cnt = 0; 
    
    public void dfs(int idx, int curr, int target, int[] numbers) {
            // 종료 조건 : 모든 숫자의 +/- 선택이 끝났는가? 
            if (idx == numbers.length){
                if (curr == target) {
                    cnt++; 
                }
                return; 
            }
            // 두 갈래로 분기 
            dfs(idx+1, curr + numbers[idx], target, numbers); 
            dfs(idx+1, curr - numbers[idx], target, numbers);
 
    }
    
    
    public int solution(int[] numbers, int target) {
        dfs(idx, curr, target, numbers); 
        return cnt;
    }
}