import java.util.LinkedList;
import java.util.Queue;

class Solution {
        public int solution(int[] priorities, int location) {
            int answer = 0;
            Queue<Integer> queue = new LinkedList<Integer>();
            for(int i = 0; i < priorities.length; i++) {
                queue.offer(priorities[i]);
            }

            while(!queue.isEmpty()) {
                int max = 0;
                for(int i = 0; i < queue.size(); i++) {
                    if(max < (int)queue.toArray()[i]) {
                        max = (int)queue.toArray()[i];
                    }
                }
                if(max == queue.peek()) {
                    answer++;
                    queue.poll();
                    if(location == 0) {
                        break;
                    } else {
                        location--;
                    }
                } else {
                    queue.offer(queue.poll());
                    if(location == 0) {
                        location = queue.size() - 1;
                    } else {
                        location--;
                    }
                }
            }
            return answer;
        }
    }