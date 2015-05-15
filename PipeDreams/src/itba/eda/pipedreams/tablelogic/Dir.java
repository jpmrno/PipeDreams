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
	
	public Dir getOpposite() {
		Dir ret = null;
		
		switch (this){
			case NORTH:
				ret = SOUTH;
				break;
			case SOUTH:
				ret = NORTH;
				break;
			case WEST:
				ret = EAST;
				break;
			case EAST:
				ret = WEST;
				break;
		}
		
		return ret;
	}
}
