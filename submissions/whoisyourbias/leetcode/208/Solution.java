import java.util.HashMap;

class Trie {

	class Node {
		HashMap<Character, Node> childs;
		boolean eow;

		Node() {
			eow = false;
			this.childs = new HashMap<>();
		}

		@Override
		public String toString() {
			return "eow" + eow;
		}
	}

	HashMap<Character, Node> childs;

	public Trie() {
		this.childs = new HashMap<>();
	}

	public void insert(String word) {
		this.childs.putIfAbsent(word.charAt(0), new Node());
		Node cur = this.childs.get(word.charAt(0));
		for (int i = 1; i < word.length(); i++) {
			cur.childs.putIfAbsent(word.charAt(i), new Node());
			cur = cur.childs.get(word.charAt(i));
		}
		cur.eow = true;
	}

	public boolean search(String word) {
		Node cur = this.childs.get(word.charAt(0));
		int c = 1;
		for (int i = 1; cur != null && i < word.length(); i++, c++) {
			cur = cur.childs.get(word.charAt(i));
		}
		if (c == word.length() && cur != null && cur.eow)
			return true;
		return false;
	}

	public boolean startsWith(String prefix) {
		Node cur = this.childs.get(prefix.charAt(0));
		int c = 1;
		for (int i = 1; cur != null && i < prefix.length(); i++, c++) {
			cur = cur.childs.get(prefix.charAt(i));
		}

		if (c == prefix.length() && cur != null)
			return true;
		return false;
	}
}
