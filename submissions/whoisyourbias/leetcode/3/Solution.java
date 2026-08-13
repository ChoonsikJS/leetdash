import java.util.*;

class Solution {

    public int lengthOfLongestSubstring(String s) {
        int max = Integer.MIN_VALUE;
		int i = 0;
		while (i < s.length()) {
			HashMap<Character, Integer> charMap = new HashMap<>();

			int end = i;
			for (; end < s.length(); end++) {
				if (charMap.containsKey(s.charAt(end))) {
                    // end -= 1;
					break;
				} else {
					charMap.put(s.charAt(end), end);
				}
			}

            max = Math.max(charMap.size(), max);
            if (end == s.length())
                break;
            if (charMap.containsKey(s.charAt(end))) {
                i = charMap.get(s.charAt(end)) + 1;
            }
        }
        if (max == Integer.MIN_VALUE)
            return 0;
        else
            return max;
    }
}
