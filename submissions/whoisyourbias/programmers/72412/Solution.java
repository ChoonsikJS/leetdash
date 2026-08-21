import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.StringTokenizer;

class Solution {
	public int[] solution(String[] infos, String[] queries) {
		int[] answer = new int[queries.length];
		StringTokenizer st;

		HashMap<String, // 언어
				HashMap<String, // 직군
						HashMap<String, // 경력
								HashMap<String, // 소울푸드
										ArrayList<Person>>>>> map = new HashMap<>();

		for (String info : infos) {
			st = new StringTokenizer(info);

			Person p = new Person(
					st.nextToken(),
					st.nextToken(),
					st.nextToken(),
					st.nextToken(),
					Integer.parseInt(st.nextToken()));

			// lang
			map.putIfAbsent(
					p.lang,
					new HashMap<String, // 직군
							HashMap<String, // 경력
									HashMap<String, // 소울푸드
											ArrayList<Person>>>>());

			map.get(p.lang).putIfAbsent(
					p.job,
					new HashMap<String, // 경력
							HashMap<String, // 소울푸드
									ArrayList<Person>>>());

			map.get(p.lang).get(p.job).putIfAbsent(
					p.age,
					new HashMap<String, // 소울푸드
							ArrayList<Person>>());

			map.get(p.lang).get(p.job).get(p.age).putIfAbsent(
					p.food,
					new ArrayList<Person>());

			map.get(p.lang).get(p.job).get(p.age).get(p.food).add(p);
		}

		for (HashMap<String, HashMap<String, HashMap<String, ArrayList<Solution.Person>>>> m1 : map.values()) {
			for (HashMap<String, HashMap<String, ArrayList<Solution.Person>>> m2 : m1.values()) {
				for (HashMap<String, ArrayList<Solution.Person>> m3 : m2.values()) {
					for (ArrayList<Solution.Person> m4 : m3.values()) {
						Collections.sort(m4, new Comparator<Person>() {
							@Override
							public int compare(Solution.Person o1, Solution.Person o2) {
								return o1.score - o2.score;
							}
						});
					}
				}
			}
		}

		int i = 0;
		for (String query : queries) {
			st = new StringTokenizer(query);

			String lang = st.nextToken();
			String _ = st.nextToken();
			String job = st.nextToken();
			String _ = st.nextToken();
			String age = st.nextToken();
			String _ = st.nextToken();
			String food = st.nextToken();
			int score = Integer.parseInt(st.nextToken());
			int c = 0;
			ArrayList<ArrayList<Person>> rst = foodQ(food, ageQ(age, jobQ(job, lngQ(lang, map))));

			for (ArrayList<Person> plst : rst) {
				c += plst.size() - lowerBound(plst, score);
			}

			answer[i++] = c;
		}

		return answer;
	}

	// 1
	// 0 100 200 300 400 500
	// target -> 100
	private int lowerBound(ArrayList<Person> lst, int target) {
		int left = 0;
		int right = lst.size();

		while (left < right) {
			int mid = left + (right - left) / 2;

			if (lst.get(mid).score >= target)
				right = mid;
			else
				left = mid + 1;

		}

		return left;
	}

	private ArrayList<ArrayList<Person>> foodQ(String food,
			ArrayList<HashMap<String, // 소울푸드
					ArrayList<Person>>> jobQResults) {
		ArrayList<ArrayList<Person>> rtn = new ArrayList<>();
		for (HashMap<String, // 소울푸드
				ArrayList<Person>> ageQResult : jobQResults) {

			if (food.equals("-")) {
				if (ageQResult.get("chicken") != null)
					rtn.add(ageQResult.get("chicken"));
				if (ageQResult.get("pizza") != null)
					rtn.add(ageQResult.get("pizza"));
			} else {
				if (ageQResult.get(food) != null)
					rtn.add(ageQResult.get(food));
			}
		}

		return rtn;
	}

	private ArrayList<HashMap<String, ArrayList<Person>>> ageQ(String age,
			ArrayList<HashMap<String, // 경력
					HashMap<String, // 소울푸드
							ArrayList<Person>>>> jobQResults) {
		ArrayList<HashMap<String, // 소울푸드
				ArrayList<Person>>> rtn = new ArrayList<>();
		for (HashMap<String, // 경력
				HashMap<String, // 소울푸드
						ArrayList<Person>>> ageQResult : jobQResults) {

			if (age.equals("-")) {
				if (ageQResult.get("junior") != null)
					rtn.add(ageQResult.get("junior"));
				if (ageQResult.get("senior") != null)
					rtn.add(ageQResult.get("senior"));
			} else {
				if (ageQResult.get(age) != null)
					rtn.add(ageQResult.get(age));
			}
		}

		return rtn;
	}

	private ArrayList<HashMap<String, HashMap<String, ArrayList<Person>>>> jobQ(String job,
			ArrayList<HashMap<String, // 직군
					HashMap<String, // 경력
							HashMap<String, // 소울푸드
									ArrayList<Person>>>>> langQResults) {
		ArrayList<HashMap<String, // 경력
				HashMap<String, // 소울푸드
						ArrayList<Person>>>> rtn = new ArrayList<>();
		for (HashMap<String, // 직군
				HashMap<String, // 경력
						HashMap<String, // 소울푸드
								ArrayList<Person>>>> langQResult : langQResults) {

			if (job.equals("-")) {
				if (langQResult.get("backend") != null)
					rtn.add(langQResult.get("backend"));
				if (langQResult.get("frontend") != null)
					rtn.add(langQResult.get("frontend"));
			} else {
				if (langQResult.get(job) != null)
					rtn.add(langQResult.get(job));
			}
		}

		return rtn;
	}

	private ArrayList<HashMap<String, // 직군
			HashMap<String, // 경력
					HashMap<String, // 소울푸드
							ArrayList<Person>>>>> lngQ(String lang, HashMap<String, // 언어
									HashMap<String, // 직군
											HashMap<String, // 경력
													HashMap<String, // 소울푸드
															ArrayList<Person>>>>> map) {

		ArrayList<HashMap<String, // 직군
				HashMap<String, // 경력
						HashMap<String, // 소울푸드
								ArrayList<Person>>>>> rtn = new ArrayList<>();

		if (lang.equals("-")) {
			if (map.get("cpp") != null)
				rtn.add(map.get("cpp"));

			if (map.get("java") != null)
				rtn.add(map.get("java"));

			if (map.get("python") != null)
				rtn.add(map.get("python"));
		} else {
			if (map.get(lang) != null)
				rtn.add(map.get(lang));
		}

		return rtn;
	}

	class Person {
		String lang;
		String job;
		String age;
		String food;
		int score;

		Person(String lang, String job, String age, String food, int score) {
			this.lang = lang;
			this.job = job;
			this.age = age;
			this.food = food;
			this.score = score;
		}
	}
}
