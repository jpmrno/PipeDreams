package itba.eda.pipedreams.tablelogic;

public class Board {
	
	private Tile[][] board;
	private int x_flow;
	private int y_flow;
	private Dir flow;

	public Board(String[] tiles){
		board = new Tile[tiles.length][tiles[0].length()];

		boolean sourceFound = false;

		for(int i=0; i < tiles.length; i++) {
			for(int j=0; j < tiles[0].length(); j++) {
				if (parseTile(tiles[i].charAt(j), i, j)) {
					if (!sourceFound) {
						sourceFound = true;
					} else if (sourceFound) {
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

	public Tile getTile(Point point){
		int x = point.getX();
		int y = point.getY();

		if(x >= board.length || x < 0 || y >= board[0].length || y < 0) {
			return null;
		} else {
			return board[x][y];
		}
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

	public void print() {
		System.out.println("-------------------------");
		for(int i=0; i < board.length; i++) {
			for(int j=0; j < board[0].length; j++) {
				Tile curr = board[i][j];
				if(curr.hasPipe()) {
					System.out.print("\t" + curr.getPipe().getId());
				} else if(curr.isBlocked()) {
					System.out.print("\t#");
				} else {
					System.out.print("\t ");
				}
			}
			System.out.println();
		}
		System.out.println("-------------------------");
	}

	public Tile getOriginTile() {
		return board[x_flow][y_flow];
	}
}
