import java.util.HashMap;

class Solution {

	// 각 인덱스별 구간합PrefixSum을 캐싱해두고,
	// PrefixSum[i] - PrefixSum[j] = k 를 만족하는 구간합 개수 agg유도.
	// 이전 기록에 대해서만 탐색하므로, 인덱스 증가시키면서 prefixSum업데이트 및
	// 구간합 개수 기록도 업데이트해나가야함.
	public int subarraySum(int[] nums, int k) {
		int c = 0;

		HashMap<Integer, Integer> map = new HashMap<>();

		int sum = 0;
		map.put(0, 1);

		for (int i = 0; i < nums.length; i++) {
			// i번째까지의 합
			sum += nums[i];

			// 현재 i번째까지 합 sum == prefix[i]
			// prefix[i] - prefix[j] = k
			// j < i
			// -> k + prefix[j] = prefix[i]
			int target = sum - k;
			// prefix[j]는 map에 보관되어있음.
			c += map.getOrDefault(target, 0);

			// prefix[i]를 과거기록에 보관
			map.put(sum, map.getOrDefault(sum, 0) + 1);
		}
		return c;
	}
}
