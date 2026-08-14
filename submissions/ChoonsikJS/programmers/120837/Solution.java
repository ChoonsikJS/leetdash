class Solution {
    public int solution(int hp) {
        int answer = 0;
        int captain_Ant = 5;
        int soldier_Ant = 3;
        int worker_Ant = 1;

        // 5 3 1 을 조합해 가장 작은 수로 hp를 0으로 만들기.
        while(hp > 0) {
            if(hp >= captain_Ant) {
                hp -= captain_Ant;
                answer++;
            } else if(hp >= soldier_Ant) {
                hp -= soldier_Ant;
                answer++;
            } else {
                hp -= worker_Ant;
                answer++;
            }
        }
        return answer;
    }
}