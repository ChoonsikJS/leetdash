import java.util.ArrayList;
import java.util.List;

class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        List<Integer> list = new ArrayList<>();
		// 두 배열의 교집합 (중복 가능)
		for (int i = 0; i < nums1.length; i++) {
			for (int j = 0; j < nums2.length; j++) {
				if (nums1[i] == nums2[j]) {
					list.add(nums1[i]);
					nums2[j] = Integer.MAX_VALUE; // 이미 사용된 원소는 무시
					break;
				}
			}
		}
		int arr[] = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		return arr;
    }
}