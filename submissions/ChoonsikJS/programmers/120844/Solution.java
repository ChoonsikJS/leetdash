class Solution {
    public int[] solution(int[] numbers, String direction) {
        int[] answer = new int[numbers.length];
        if (direction.equals("right")) {
            answer[0] = numbers[numbers.length - 1];
            for (int i = 1; i < numbers.length; i++) {
                answer[i] = numbers[i - 1];
            }
        } else {
            answer[answer.length - 1] = numbers[0];
            for (int i = 0; i < numbers.length - 1; i++) {
                answer[i] = numbers[i + 1];
            }
        }
        return answer;
    }
}
// 리스트로 바꿨다가 다시 배열로 바꿔서 반환
// List<Integer> list = new ArrayList<>();
// for(int i = 0; i < numbers.length; i++) {
//     list.add(numbers[i]);
// }                
// if(direction.equals("right")) {
//     int last = list.get(list.size() - 1);
//     list.remove(list.size() - 1);
//     list.add(0, last);
// } else {
//     int first = list.get(0);
//     list.remove(0);
//     list.add(first);
// }
// for(int i = 0; i < list.size(); i++) {
//     answer[i] = list.get(i);
// }
// return answer;