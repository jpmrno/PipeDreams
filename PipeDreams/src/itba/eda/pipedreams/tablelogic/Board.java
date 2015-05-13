package itba.eda.pipedreams.tablelogic;

public class Board {
	
	private Tile[][] board;
	
	public Board(int dimX, int dimY){
		board = new Tile[dimX][dimY];
		
		for (int i = 0; i < dimX; i++){
			for (int j = 0; j < dimY; j++){
				board[i][j] = new Tile(i, j);
			}
		}
	
	}
}
