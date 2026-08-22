class Solution {
    public int[] solution(int[] arr) {   
        // return 배열이 int[] 니까 처음부터 int[] 로 
        int size = 0; 
        for (int elem : arr) {
            size += elem; 
        }
        int[] result = new int[size]; 
        
        int idx = 0; 
        for (int elem : arr) {
            for (int i = 0; i < elem; i++) {
                result[idx] = elem;
                idx++; 
            }
        }
        return result; 
    }
}