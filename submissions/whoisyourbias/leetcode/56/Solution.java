import java.util.*;

class Solution {
	public int[][] merge(int[][] intervals) {

		PriorityQueue<Integer[]> lst = new PriorityQueue<>(
				new Comparator<Integer[]>() {
					@Override
					public int compare(Integer[] i1, Integer[] i2) {
						return i1[0] - i2[0];
					}
				});

		for (int[] v : intervals) {
			Integer[] a = new Integer[2];
			a[0] = v[0];
			a[1] = v[1];
			lst.add(a);
		}

		ArrayList<Integer[]> answer = new ArrayList<>();

		while (!lst.isEmpty()) {
			if (lst.size() == 1) {
				answer.add(lst.poll());
				break;
			}

			Integer[] v1 = lst.poll();
			Integer[] v2 = lst.poll();

			// check collapse
			if (v1[1] >= v2[0]) {
				// collapsed
				// merge them and re-insert
				Integer[] n = new Integer[2];
				n[0] = Math.min(v1[0], v2[0]);
				n[1] = Math.max(v1[1], v2[1]);
				lst.add(n);
			} else {
				// not collapsed
				// v1 goes to answer
				answer.add(v1);
				lst.add(v2);
			}
		}

		for (int i = 0; i < answer.size(); i++)
			System.out.printf("%d %d\n", answer.get(i)[0], answer.get(i)[1]);

		int[][] rtn = new int[answer.size()][2];
		for (int i = 0; i < answer.size(); i++) {
			rtn[i][0] = answer.get(i)[0];
			rtn[i][1] = answer.get(i)[1];

		}
		return rtn;
	}
}
