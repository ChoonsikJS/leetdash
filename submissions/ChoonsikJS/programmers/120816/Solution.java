class Solution {
    public int solution(int slice, int n) {
        // slice x answer / n > 1
        for (int i = 1; i < n*2 ; i++) {
        	int pieces = slice * i;
			if(pieces / n >= 1) return i;
		}
        return -1;
    }
}