import java.util.LinkedList;
import java.util.Queue;

class Solution {

    public int solution(String begin, String target, String[] words) {

        boolean targetExist = false;

        for(String word : words) {
            if(word.equals(target)) {
                targetExist = true;
                break;
            }
        }

        if(!targetExist) {
            return 0;
        }

        int answer = 0;

        boolean[] visited = new boolean[words.length];
        Queue<String> q = new LinkedList<>();

        q.offer(begin);

        while(!q.isEmpty()) {

            int size = q.size();

            for(int i = 0; i < size; i++) {

                String cur = q.poll();

                if(cur.equals(target)) {
                    return answer;
                }

                for(int j = 0; j < words.length; j++) {

                    if(visited[j]) continue;

                    int diff = 0;

                    for(int k = 0; k < cur.length(); k++) {
                        if(cur.charAt(k) != words[j].charAt(k)) {
                            diff++;
                        }
                    }

                    if(diff == 1) {
                        visited[j] = true;
                        q.offer(words[j]);
                    }
                }
            }

            answer++;
        }

        return 0;
    }
}