class Solution {
    public int solution(int price) {
    	//50 30 10 순서
    	if (price >= 500000) {
    		price -= price * 0.2;
    		return price;
    	}else if (price >= 300000) {
    		price -= price * 0.1;
    		return price;
		}else if (price >= 100000) {
			price -= price * 0.05;
			return price;
		}else return price;
    }
}