package itba.eda.pipedreams.tablelogic;

public enum Dir {
	NORTH,
	SOUTH,
	EAST,
	WEST;

	Dir opposite;

	static {
		NORTH.opposite = SOUTH;
		SOUTH.opposite = NORTH;
		EAST.opposite = WEST;
		WEST.opposite = EAST;
	}

	public static Dir getBySymbol(char c){
		switch (Character.toUpperCase(c)){
			case 'N':
				return NORTH;
			case 'S':
				return SOUTH;
			case 'W':
				return WEST;
			case 'E':
				return EAST;
			default:
				return null;
		}
	}
	
	public Dir opposite() {
		return opposite;
	}
}
