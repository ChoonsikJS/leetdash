// int[] box : 가로, 세로, 높이 
// n : 주사위 모서리 정수 
class Solution {
    public int solution(int[] box, int n) {
        return (box[0] / n) * (box[1] / n) * (box[2] / n);
    }
}