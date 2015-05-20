package itba.eda.pipedreams.tablelogic;

public enum GamePipe implements BasicGamePipe {
	L1("WN"),
	L2("EN"),
	L3("ES"),
	L4("WS"),
	I1("SN"),
	I2("WE"),
	CROSS("SNWE");

	private PairDir[] directions;

	GamePipe(String dirs) {
		if(dirs == null || dirs.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}

		this.directions = new PairDir[dirs.length() / PairDir.SIZE];

		for(int i = 0; i < directions.length; i++) {
			Dir first = Dir.getBySymbol(dirs.charAt(i * PairDir.SIZE));
			Dir second = Dir.getBySymbol(dirs.charAt(i * PairDir.SIZE + 1));
			directions[i] = new PairDir(first , second);
		}
	}

	@Override
	public boolean canFlow(Dir from) {
		for(PairDir dirs : directions) {
			if(dirs.contains(from)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Dir flow(Dir from) {
		for(PairDir dirs : directions) {
			Dir to = dirs.getTo(from);
			if(to != null) {
				return to;
			}
		}
		return null;
	}
}
