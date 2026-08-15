import java.util.*;

class Solution {
	public int solution(int[][] targets) {
		int answer = 0;

		ArrayList<Integer[]> lst = new ArrayList<Integer[]>();

		for (int[] v : targets) {
			Integer[] a = new Integer[2];
			a[0] = v[0];
			a[1] = v[1];
			lst.add(a);
		}

		Collections.sort(lst, new Comparator<Integer[]>() {
			@Override
			public int compare(Integer[] a1, Integer[] a2) {
				return a1[1] - a2[1];
			}
		});

		int cur = lst.get(0)[1];

		int i = 1;
		answer = 1;
		while (i < lst.size()) {
			if (cur <= lst.get(i)[1] && cur > lst.get(i)[0])
				i++;
			else {
				answer += 1;
				cur = lst.get(i)[1];
			}
		}

		return answer;
	}
}
