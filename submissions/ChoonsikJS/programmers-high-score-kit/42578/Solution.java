import java.util.ArrayList;
import java.util.HashMap;

class Solution {
    public int solution(String[][] clothes) {
        HashMap<String, ArrayList<String>> clothesByType = new HashMap<>();

        for (String[] cloth : clothes) {
            clothesByType
                .computeIfAbsent(cloth[1], type -> new ArrayList<>())
                .add(cloth[0]);
        }

        int answer = 1;
        for (ArrayList<String> clothesOfType : clothesByType.values()) {
            answer *= clothesOfType.size() + 1;
        }

        return answer - 1;
    }
}