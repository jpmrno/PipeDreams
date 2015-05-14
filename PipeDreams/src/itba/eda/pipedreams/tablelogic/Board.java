package itba.eda.pipedreams.tablelogic;

public class Board {
	//private static Board instance = null;
	
	private Tile[][] board;
	private Point start;
	
	private static final String[] example = {"##  ##", "  # #", "#  N  ", "#     ", "###   "};
	
	/*public static Board getInstance(){
		
	}*/
	
	private Board(int dimX, int dimY) {
		board = new Tile[dimX][dimY];
		
		for (int i = 0; i < dimX; i++) {
			for (int j = 0; j < dimY; j++) {
				board[i][j] = new Tile(i, j);
			}
		}
	}

	public boolean withinLimits(Point pos) {
		return pos.getX() <= board.length && pos.getY() <= board[0].length;
	}
}
