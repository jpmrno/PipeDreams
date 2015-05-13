package itba.eda.pipedreams.tablelogic;

public enum Dir {
	NORTH,
	SOUTH,
	EAST,
	WEST;
	
	public static Dir getBySymbol(String str){
		switch (str){
			case "N":
				return NORTH;		
			case "S":
				return SOUTH;
			case "W":
				return WEST;
			case "E":
				return EAST;
			default:
				return null;
		}
	}
	
	public static Dir invert(Dir dir) {
		switch (dir){
		case NORTH:
			return SOUTH;		
		case SOUTH:
			return NORTH;
		case WEST:
			return EAST;
		case EAST:
			return WEST;
		default:
			return null;
		}
	}
}
