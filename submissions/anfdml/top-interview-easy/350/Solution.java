import java.util.ArrayList;

public class Solution {
public int[] intersect(int[] nums1, int[] nums2) {
	       ArrayList<Integer> arr = new ArrayList<>();
	       boolean[] use = new boolean[nums2.length];
		 for (int i = 0; i < nums1.length; i++) {
				for (int j = 0; j < nums2.length; j++) {
					if(!use[j]&&nums1[i]==nums2[j]) {
						 arr.add(nums1[i]);
						 use[j]=true;
						 break;
					}
				}
			}
		 
		 return arr.stream()
                 .mapToInt(Integer::intValue)
                 .toArray();
	    }
	}