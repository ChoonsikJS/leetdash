/*
[조건 정리]
- 원하는 순서의 단어 배열 만드는게 가능할지 
- 카드 재사용 불가능
- 카드 사용하지 않고 다음 카드로 넘어갈 수 없음
- 단어 순서 변경 불가능

[알고리즘 구상]
- card1, 2의 위치를 계속 파악해야 함 !! 
- card1과 2, goal의 index를 배열로 담아놓기
- goal[i]랑 card1, 2 위치의 단어를 비교하기
    -> 같은게 있다면 그 카드의 위치++, goal의 단어도++
    -> 만약 없다면 return no
*/
class Solution {
    public String solution(String[] cards1, String[] cards2, String[] goal) {
        
        int cards1Idx = 0;
        int cards2Idx = 0;
        int goalIdx = 0;
        while(goalIdx < goal.length){
                if(cards1Idx < cards1.length && goal[goalIdx].equals(cards1[cards1Idx])){
                goalIdx++;
                cards1Idx++;
                } else {
                    if(cards2Idx < cards2.length && goal[goalIdx].equals(cards2[cards2Idx])){
                        goalIdx++;
                        cards2Idx++;
                    } else return "No";
                }         
             }
        return "Yes";
        
    } 
               
}