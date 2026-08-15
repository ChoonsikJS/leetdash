import java.util.*;

class Solution {
    public int solution(int n, int[][] costs) {

        // 모든 다리를 비용 오름차순으로 정리.
        Arrays.sort(costs, (o1, o2) -> Integer.compare(o1[2], o2[2]));

        // 섬 그룹를 표시하는 배열을 만들고 각 섬은 자기 번호로 초기화.
        int[] group = new int[n];
        for(int i = 0; i < n; i++){
            group[i] = i;
        }

        // 가장 싼 다리부터 선택 -> 그룹이 다르면 다리 추가 + 그룹 숫자 작은걸로 통일
        int total_cost = 0;
        int count = 0;
        for(int i = 0; i < costs.length; i++) {
            int land1 = costs[i][0];
            int land2 = costs[i][1];
            if(group[land1] != group[land2]){
                int g1 = group[land1];
                int g2 = group[land2];

                for(int j = 0; j < n; j++){
                    if(group[j] == g1){
                        group[j] = g2;
                    }
                }

                total_cost += costs[i][2];
                count++;
            } else{
                continue;
            }

            // 선택한 다리가 n-1이면 종료
            if(count == n-1){
                break;
            }
        }

        return total_cost;
    }
}