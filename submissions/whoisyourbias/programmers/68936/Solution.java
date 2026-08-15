class Solution {
	class Zoc {
		int oc = 0;
		int zc = 0;
	}

	public int[] solution(int[][] arr) {
		int[] answer = new int[2];

		int n = arr.length;

		Zoc zoc = getn(arr, 0, n, 0, n);

		answer[0] = zoc.zc;
		answer[1] = zoc.oc;
		return answer;
	}

	private Zoc getn(int[][] arr,
			int ifrom, int ito, int jfrom, int jto) {
		Zoc zoc = new Zoc();
		boolean call_next = false;
		for (int i = ifrom; i < ito; i++) {
			for (int j = jfrom; j < jto; j++) {
				if (arr[i][j] == 0)
					zoc.zc++;
				else
					zoc.oc++;
			}
		}
		if (zoc.zc != 0 && zoc.oc != 0)
			call_next = true;

		if (call_next) {
			int middleI = (ifrom + ito) / 2;
			int middleJ = (jfrom + jto) / 2;
			Zoc one = getn(arr, ifrom, middleI, jfrom, middleJ);

			Zoc two = getn(arr, ifrom, middleI, middleJ, jto);

			Zoc three = getn(arr, middleI, ito, jfrom, middleJ);

			Zoc four = getn(arr, middleI, ito, middleJ, jto);

			Zoc rtn = new Zoc();
			rtn.oc = one.oc + two.oc + three.oc + four.oc;
			rtn.zc = one.zc + two.zc + three.zc + four.zc;
			return rtn;
		} else {
			if (zoc.zc == 0)
				zoc.oc = 1;
			else
				zoc.zc = 1;
			return zoc;
		}
	}
}
