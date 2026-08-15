class Solution {
    public int solution(int[] box, int n) {
        int width = box[0];
        int depth = box[1];
        int height = box[2];
        int quantity = (width/n) * (depth/n) * (height/n);
        return quantity;
    }
}