class Solution {
    public int solution(String name) {
        int cnt = 0;
        // (Default) 오른쪽 끝까지 가는 경우 
        int defaultCnt = name.length() - 1; 
        for (int i = 0; i < name.length(); i++) {
            
            // 상하 이동  
            int idx = name.charAt(i) - 'A'; 
            cnt += idx > 13? 26 - idx: idx; 
            
            // next : 현재 연속된 "A"를 전부 건너뛴 뒤, 다음에 실제로 방문해야하는 위치 
            int next = i+1; 
            while (next < name.length() && name.charAt(next) == 'A') { 
                next++; 
            }
            
            // 오른쪽 먼저 갔다가 되돌아가기
            // 왼쪽 먼저 갔다가 되돌아가기 
            int rightMove = i * 2 + (name.length() - next); 
            int leftMove = (name.length() - next ) * 2 + i; 
            
            // 기존 좌우 최솟값과 비교 
            defaultCnt = Math.min(defaultCnt, Math.min(rightMove, leftMove)); 

                
        }
        return cnt + defaultCnt;
    }
}