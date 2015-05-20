package itba.eda.pipedreams.tablelogic;

public class PairDir {
	public static final int SIZE = 2;

	private Dir first;
	private Dir second;

	public PairDir(Dir first, Dir second) {
		this.first = first;
		this.second = second;
	}

	public Dir getFirst() {
		return first;
	}

	public Dir getSecond() {
		return second;
	}

	public boolean contains(Dir dir) {
		return first == dir || second == dir;
	}

	public Dir getTo(Dir from) {
		if(first == from) {
			return second;
		}
		if(second == from) {
			return first;
		}
		return null;
	}
}
