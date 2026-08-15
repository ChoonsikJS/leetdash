import java.util.*;

class Solution {
	public int solution(int[][] board, int[][] skills) {
		int broken = 0;

		int[][] lmos = new int[board.length][board[0].length];

		for (int[] skill : skills) {
			int type = skill[0];
			int r1 = skill[1];
			int c1 = skill[2];
			int r2 = skill[3];
			int c2 = skill[4];
			int degree = skill[5];

			int diff = type == 1 ? -degree : +degree;
			/*
			 * r1 0
			 * c1 1
			 * r2 1
			 * c2 1
			 * lmos 표현 ->
			 * 4 0 -4
			 * 0 0 0
			 * -4 0 4
			 * 
			 * 1. 가로누적합
			 * 4 4 0
			 * 0 0 0
			 * -4-4 0
			 * 
			 * 2. 세로누적합
			 * 4 4 0
			 * 4 4 0
			 * 0 0 0
			 */
			lmos[r1][c1] += diff;
			if (c2 + 1 < board[0].length)
				lmos[r1][c2 + 1] -= diff;
			if (r2 + 1 < board.length)
				lmos[r2 + 1][c1] -= diff;
			if (r2 + 1 < board.length &&
					c2 + 1 < board[0].length)
				lmos[r2 + 1][c2 + 1] += diff;
		}

		for (int i = 0; i < lmos.length; i++) {
			for (int j = 0; j < lmos[0].length; j++) {
				if (j - 1 >= 0)
					lmos[i][j] += lmos[i][j - 1];
			}
		}

		for (int j = 0; j < lmos[0].length; j++) {
			for (int i = 0; i < lmos.length; i++) {
				if (i - 1 >= 0)
					lmos[i][j] += lmos[i - 1][j];
				if (board[i][j] + lmos[i][j] <= 0)
					broken++;
			}
		}

		return board.length * board[0].length - broken;
	}
}
