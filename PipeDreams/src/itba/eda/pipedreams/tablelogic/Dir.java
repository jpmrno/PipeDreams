package itba.eda.pipedreams.tablelogic;

public enum Dir {
	NORTH,
	SOUTH,
	EAST,
	WEST;
	
	public static Dir getBySymbol(String str){
		
		Dir ret;
		switch (str){
			
			case "N":
				ret = NORTH;
				break;
			
			case "S":
				ret = SOUTH;
				break;
				
			case "W":
				ret = WEST;
				break;
				
			case "E":
				ret = EAST;
				break;
				
			default:
				ret = null;
		}
		
		return ret;
	}
}
