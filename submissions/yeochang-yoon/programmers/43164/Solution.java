import java.util.*;
import java.util.Collections.*;

class Solution {
    public String[] solution(String[][] tickets) {

        Map<String, List<String>> map = new HashMap<>();

        for(int i = 0; i < tickets.length; i++){
            map.putIfAbsent(tickets[i][0], new ArrayList<String>());
            map.get(tickets[i][0]).add(tickets[i][1]);
        }

        for(String key : map.keySet()){
            map.get(key).sort(null);
        }

        List<String> result = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        stack.push("ICN");

        while(!stack.isEmpty()){
            String start = stack.peek();



            if(!map.containsKey(start) || map.get(start).size() == 0){
                result.add(stack.pop());
                continue;
            }
            stack.push(map.get(start).remove(0));
        }
        Collections.reverse(result);
        String[] answer = result.toArray(new String[0]);
        return answer;
    }
}