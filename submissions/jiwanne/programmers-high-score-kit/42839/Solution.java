import java.util.Collections;
import java.util.HashSet;

class Solution {

    private static boolean[] prime;
    HashSet<Integer> set;

    static void make_prime(int n , boolean[] prime) {
        prime[0] = prime[1] = false;
        for(int i = 2; i <= Math.sqrt(n); i++) {
            if(prime[i]) {
                for(int j = i * i; j <= n; j += i) {
                    prime[j] = false;
                }
            }
        }
    }

    public void dfs(int[] arr , boolean[] visited, String str, int len) {

        if(str.length() == len) {
            int num = Integer.parseInt(str);
                set.add(num);
                return;
        }
        for(int i = 0; i < arr.length; i++) {

            if(visited[i]) continue;
            visited[i] = true;

            dfs(arr, visited, str + arr[i],len);
            visited[i] = false;
        }
    }

    
    public int solution(String numbers) {
        int answer = 0;
        int[] arr = new int[numbers.length()];

        set = new HashSet<>();

        for(int i =0; i < numbers.length(); i++) {
            arr[i] = numbers.charAt(i) - '0';
        }

        for(int i = 1; i <= numbers.length(); i++) {
            boolean[] visited = new boolean[numbers.length()];
            dfs(arr, visited, "", i);
        }

        int max = Collections.max(set);
        prime = new boolean[max + 1];
         for(int i = 0; i < max + 1; i++) {
            prime[i] = true;
        }
        make_prime(max, prime);

        for(int num : set) {
            if(prime[num]) {
                answer++;
            }
        }
        return answer;
    }
}