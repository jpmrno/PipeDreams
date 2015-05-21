package itba.eda.pipedreams.tablelogic;

import itba.eda.pipedreams.pipelogic.Pipe;

public class Board {

	private Tile[][] board;
	private Point startPoint;
	private Dir startFlow;

	public Board(int rows, int columns, String[] tiles) {
		board = new Tile[rows][columns];

		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[0].length(); j++) {
				if(setPiece(tiles[i].charAt(j), i, j) == null) {
					throw new IllegalArgumentException("Invalid board.");
				}
			}
		}
	}

	public boolean setPipe(Pipe pipe, Point point) {
		if(!withinLimits(point)) {
			throw new IndexOutOfBoundsException();
		}

		if(board[point.getRow()][point.getColumn()] == Tile.EMPTY) {
			Tile tile = Tile.get(pipe);

			if(tile != null) {
				board[point.getRow()][point.getColumn()] = tile;
				return true;
			}
		}

		return false;
	}

	public boolean removePipe(Point point) {
		if(!withinLimits(point)) {
			throw new IndexOutOfBoundsException();
		}

		if(board[point.getRow()][point.getColumn()].getPipe() != null) {
			board[point.getRow()][point.getColumn()] = Tile.EMPTY;
			return true;
		}

		return false;
	}

	private Tile setPiece(char c, int row, int column) {
		Tile piece;

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
				piece =  Tile.valueOf("START_" + c);
				break;
			case ' ':
				piece =  Tile.EMPTY;
				break;
			case '#':
				piece =  Tile.WALL;
				break;
			default:
				return null;
		}

		board[row][column] = piece;
		return piece;
	}

	public Pipe get(Point point) { // TODO: not empty
		if(!withinLimits(point)) {
			throw new IndexOutOfBoundsException();
		}

		return board[point.getRow()][point.getColumn()].pipe;
	}

	public static Point getNext(Point point, Dir dir) {
		switch(dir) {
			case NORTH:
				return new Point(point.getRow() - 1, point.getColumn());
			case SOUTH:
				return new Point(point.getRow() + 1, point.getColumn());
			case WEST:
				return new Point(point.getRow(), point.getColumn() - 1);
			case EAST:
				return new Point(point.getRow(), point.getColumn() + 1);
			default:
				throw new IllegalStateException();
		}
	}

	public boolean isEmpty(Point point) {
		if(!withinLimits(point)) {
			throw new IndexOutOfBoundsException();
		}

		return board[point.getRow()][point.getColumn()] == Tile.EMPTY;
	}

	public boolean withinLimits(Point point) {
		return point.getRow() >= 0 && point.getRow() < board.length && point.getColumn() >= 0 && point.getColumn() < board[0].length;
	}

	public boolean isBlocked(Point point) {
		return !isEmpty(point) && board[point.getRow()][point.getColumn()].getPipe() == null;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public Dir getStartFlow() {
		return startFlow;
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		for(Tile[] row : board) {
			for(Tile piece : row) {
				ret.append(piece).append('\t');
			}
			ret.append('\n');
		}
		return ret.toString();
	}

	public void print() {
		for(Tile[] row : board) {
			for(Tile piece : row) {
				System.out.print(piece.toString() + '\t');
			}
			System.out.println();
		}
	}

	public static enum Tile {
		L1("1", Pipe.L1),
		L2("2", Pipe.L2),
		L3("3", Pipe.L3),
		L4("4", Pipe.L4),
		I1("5", Pipe.I1),
		I2("6", Pipe.I2),
		CROSS("7", Pipe.CROSS),
		EMPTY(".", null),
		WALL("#", null),
		START_N("N", null),
		START_S("S", null),
		START_E("E", null),
		START_W("W", null);

		private String representation;
		private Pipe pipe;

		private Tile(String representation, Pipe pipe) {
			if(representation == null) {
				throw new IllegalArgumentException();
			}

			this.representation = representation;
			this.pipe = pipe;
		}

		public Pipe getPipe() {
			return pipe;
		}

		@Override
		public String toString() {
			return representation;
		}

		public static Tile get(Pipe pipe) {
			switch(pipe) {
				case L1:
					return Tile.L1;
				case L2:
					return Tile.L2;
				case L3:
					return Tile.L3;
				case L4:
					return Tile.L4;
				case I1:
					return Tile.I1;
				case I2:
					return Tile.I2;
				case CROSS:
					return Tile.CROSS;
				default:
					return null;
			}
		}
	}

}
