package itba.eda.pipedreams.tablelogic;

public class Board {
	private static Board instance = null;
	
	private Tile[][] board;
	private int x_flow;
	private int y_flow;
	private Dir flow;
	
	//private static final String[] example = {"##  ##", "  # #", "#  N  ", "#     ", "###   "};


	public static Board getInstance(){

		if (instance == null){
			instance = new Board();
		}
		return instance;
	}
	
	private Board() {
		
	}
	
	public void loadBoard(String[] tiles){
		board = new Tile[tiles.length][tiles[0].length()];

		boolean sourceFound = false;

		for(int i=0; i < tiles.length; i++) {
			for(int j=0; j < tiles[0].length(); j++) {
				if (parseTile(tiles[i].charAt(j), i, j)) {
					if (sourceFound == false) {
						sourceFound = true;
					} else if (sourceFound == true) {
						throw new IllegalArgumentException("Illegal board");
					}
				}
			}
		}
	}

		/**
		 * @return returns true if a source is found
 		 */
	private boolean parseTile(Character c, int i, int j) {
		switch(Character.toUpperCase(c)) {

			case '#':
				board[i][j] = new Tile(i, j, true);
				return false;

			case ' ':
				board[i][j] = new Tile(i, j, false);
				return false;

			case 'N':
			case 'S':
			case 'W':
			case 'E':
				board[i][j] = new Tile(i, j, true);
				x_flow = i;
				y_flow = j;
				flow = Dir.getBySymbol(c.toString());
				return true;

			default:
				throw new IllegalArgumentException("Illegal Board");
		}
	}

	public Tile getTile(int x, int y){
		return board[x][y];
	}
	
	public void setFlow(int x, int y, Dir dir){
		x_flow = x;
		y_flow = y;
		flow = dir;
	}

	public int getXFlow(){
		return x_flow;
	}
	
	public int getYFlow(){
		return y_flow;
	}
	
	public Dir getDirFlow(){
		return flow;
	}
	
	public boolean TileExists(int x, int y) {
		return x >= 0 && x < board.length && y >= 0 && y < board[0].length;
	}

}
