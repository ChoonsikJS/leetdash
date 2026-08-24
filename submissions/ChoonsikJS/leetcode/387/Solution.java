import java.util.HashMap;

class Solution {
    public int firstUniqChar(String s) {
        HashMap <Character,Integer> arr = new HashMap<Character,Integer>();
        for (int i=0; i<s.length(); i++){
            arr.put(s.charAt(i), arr.getOrDefault(s.charAt(i),0)+1);
        }
        for (int i=0; i<s.length(); i++){
            if (arr.get(s.charAt(i))==1){
                return i;
            }
        }
        return -1;
    }
}