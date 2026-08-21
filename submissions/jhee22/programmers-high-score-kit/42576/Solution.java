// import java.util.*; 
// class Solution {
//     public String solution(String[] participant, String[] completion) {
//         HashMap<String, Integer> map = new HashMap<>(); 
//         // 참가자 
//         for (String p : participant) {
//             map.put(p, map.getOrDefault(p, 0) + 1); 
//         }
        
//         // 완주자 
//         for (String c : completion) {
//             map.put(c, map.getOrDefault(c, 0) -1); 
//         }
        
//         // value = 1 인 key 값 꺼내기
//         for (Map.Entry<String, Integer> entry : map.entrySet()){
//             if (entry.getValue() == 1) {
//                 return entry.getKey(); 
//             }
//         }
//         return "";
//     }
// }


// 2트 
import java.util.*; 
class Solution {
    public String solution(String[] participant, String[] completion) {
        String answer = "";
        // HashMap 선언 
        HashMap <String, Integer> partMap = new HashMap<>(); 
        
        // put(k,v)
        for (int i = 0; i < participant.length; i++) {
            partMap.put(participant[i], partMap.getOrDefault(participant[i], 0)+1);
        }
        
        for (int i = 0; i < completion.length; i++) {
            partMap.put(completion[i], partMap.getOrDefault(completion[i], 0)-1); 
        }
        
        // Map 순회 
        for (Map.Entry<String, Integer> entry : partMap.entrySet()) {
            if (entry.getValue() > 0) {
                answer = entry.getKey(); 
                break; 
            }
        }
        
        
        return answer;
    }
}