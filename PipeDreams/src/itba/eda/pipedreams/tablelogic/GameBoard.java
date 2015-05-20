package itba.eda.pipedreams.tablelogic;

public class GameBoard {

	private GameTile[][] board;
	private Point startPoint;
	private Dir startFlow;

	public GameBoard(int rows, int columns, String[] tiles) {
		board = new GameTile[rows][columns];

		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[0].length(); j++) {
				if(setPiece(tiles[i].charAt(j), i, j) == null) {
					throw new IllegalArgumentException("Invalid board.");
				}
			}
		}
	}

	public boolean setPipe(GamePipe pipe, int row, int column) {
		if(canSetPipe(row, column)) {
			GameTile tile = GameTile.get(pipe);

			if(tile != null) {
				board[row][column] = tile;
				return true;
			}
		}

		return false;
	}

	public boolean canSetPipe(int row, int column) {
		return board[row][column] == GameTile.EMPTY;
	}

	private GameTile setPiece(char c, int row, int column) {
		GameTile piece;

		switch(Character.toUpperCase(c)) {
			case 'N':
			case 'S':
			case 'W':
			case 'E':
				if(startPoint != null) {
					return null;
				}
				startFlow = Dir.getBySymbol(c);
				startPoint = new Point(row, column);
				piece =  GameTile.valueOf("START_" + c);
				break;
			case ' ':
				piece =  GameTile.EMPTY;
				break;
			case '#':
				piece =  GameTile.WALL;
				break;
			default:
				return null;
		}

		board[row][column] = piece;
		return piece;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public Dir getStartFlow() {
		return startFlow;
	}

	public GameTile get(int row, int column) {
		return board[row][column];
	}

	public void print() {
		for(GameTile[] row : board) {
			for(GameTile piece : row) {
				System.out.print(piece.toString() + '\t');
			}
			System.out.println();
		}
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		for(GameTile[] row : board) {
			for(GameTile piece : row) {
				ret.append(piece).append('\t');
			}
			ret.append('\n');
		}
		return ret.toString();
	}

	public static enum GameTile {
		L1("1", GamePipe.L1),
		L2("2", GamePipe.L2),
		L3("3", GamePipe.L3),
		L4("4", GamePipe.L4),
		I1("5", GamePipe.I1),
		I2("6", GamePipe.I2),
		CROSS("7", GamePipe.CROSS),
		EMPTY(".", null),
		WALL("#", null),
		START_N("N", null),
		START_S("S", null),
		START_E("E", null),
		START_W("W", null);

		private String representation;
		private GamePipe pipe;

		private GameTile(String representation, GamePipe gamePipe) {
			if(representation == null) {
				throw new IllegalArgumentException();
			}

			this.representation = representation;
			this.pipe = gamePipe;
		}

		public GamePipe getPipe() {
			return pipe;
		}

		@Override
		public String toString() {
			return representation;
		}

		public static GameTile get(GamePipe pipe) {
			switch(pipe) {
				case L1:
					return GameTile.L1;
				case L2:
					return GameTile.L2;
				case L3:
					return GameTile.L3;
				case L4:
					return GameTile.L4;
				case I1:
					return GameTile.I1;
				case I2:
					return GameTile.I2;
				case CROSS:
					return GameTile.CROSS;
				default:
					return null;
			}
		}
	}

}
