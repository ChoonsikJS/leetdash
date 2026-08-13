import java.util.*;

class Solution {
    public String solution(int[] numbers) {
        String answer = "";
        List<String> list = new ArrayList<String>();
        
        for(int i = 0; i < numbers.length; i++) {
            list.add(String.valueOf(numbers[i]));            
        }
        
        Collections.sort(list , new Comparator<String>() {
            @Override
            public int compare(String o1 , String o2) {
                String str1 = o1 + o2;
                String str2 = o2 + o1;
                return str2.compareTo(str1);

            }
        });
        
        for(String s : list) {
            answer += s;
        }
        if(answer.startsWith("0")) answer = "0";
        return answer;
    }
}