package itba.eda.pipedreams.solver.board;

import itba.eda.pipedreams.solver.pipe.Pipe;

public class Board implements BasicBoard {

	private Tile[][] board;
	private Point startPoint;
	private Dir startFlow;

	public Board(String[] tiles) {
		board = new Tile[tiles.length][tiles[0].length()];

		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[0].length(); j++) {
				if(setPiece(tiles[i].charAt(j), i, j) == null) {
					throw new IllegalArgumentException("Invalid board. Too many starting points or invalid tile.");
				}
			}
		}

		if(startPoint == null) {
			throw new IllegalArgumentException("Invalid board. No starting point.");
		}
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

	@Override
	public Pipe getPipe(Point point) {
		return board[point.getRow()][point.getColumn()].pipe;
	}

	@Override
	public boolean putPipe(Pipe pipe, Point point) {
		if(isEmpty(point)) { // TODO: Remove check?
			board[point.getRow()][point.getColumn()] = Tile.get(pipe);
		}

		return false;
	}

	@Override
	public boolean removePipe(Point point) {
		if(hasPipe(point)) {
			board[point.getRow()][point.getColumn()] = Tile.EMPTY;
			return true;
		}

		return false;
	}

	public static Point getNext(Point point, Dir dir) {
		int row = point.getRow();
		int column = point.getColumn();

		switch(dir) {
			case NORTH:
				point.setRow(row - 1);
				break;
			case SOUTH:
				point.setRow(row + 1);
				break;
			case WEST:
				point.setColumn(column - 1);
				break;
			case EAST:
				point.setColumn(column + 1);
				break;
			default:
				throw new IllegalStateException();
		}

		return point;
	}

	public static Point getPrevious(Point point, Dir dir) {
		return getNext(point, dir.opposite());
	}

	@Override
	public boolean isEmpty(Point point) {
		return board[point.getRow()][point.getColumn()] == Tile.EMPTY;
	}

	@Override
	public boolean hasPipe(Point point) {
		return board[point.getRow()][point.getColumn()].getPipe() != null;
	}

	@Override
	public boolean isBlocked(Point point, Dir from) {
		Tile tile = board[point.getRow()][point.getColumn()];

		return tile != Tile.EMPTY && (tile.pipe == null || tile.pipe.flow(from) == null);
	}

	@Override
	public boolean withinLimits(Point point) {
		return point.getRow() >= 0 && point.getRow() < board.length && point.getColumn() >= 0 && point.getColumn() < board[0].length;
	}

	@Override
	public Point getStartPoint() {
		return startPoint;
	}

	@Override
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

	@Override
	public int getRowSize() {
		return board.length;
	}

	@Override
	public int getColumnSize() {
		return board[0].length;
	}

	@Override
	public String getRepresentation(Point point) {
		return board[point.getRow()][point.getColumn()].toString();
	}

	private static enum Tile {
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
