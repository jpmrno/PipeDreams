package itba.eda.pipedreams.tablelogic;

public class Board {
<<<<<<< HEAD
	//private static Board instance = null;
=======
	private static Board instance = null;
>>>>>>> alg
	
	private Tile[][] board;
	private int x_flow;
	private int y_flow;
	private Dir flow;
	
	//private static final String[] example = {"##  ##", "  # #", "#  N  ", "#     ", "###   "};
	
<<<<<<< HEAD
	/*public static Board getInstance(){
		
	}*/
	
	private Board(int dimX, int dimY) {
		board = new Tile[dimX][dimY];
=======
	public static Board getInstance(){
>>>>>>> alg
		
		if (instance == null){
			instance = new Board();
		}
		return instance;
	}
	
	private Board() {
		
	}
	
	public void loadBoard(int x_dim, int y_dim){
		
		board = new Tile[x_dim][y_dim];
		
		for (int i = 0; i < x_dim; i++) {
			for (int j = 0; j < y_dim; j++) {
				board[i][j] = new Tile(i, j);
				//TODO: set Tile
			}
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
