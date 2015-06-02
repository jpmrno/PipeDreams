package itba.eda.pipedreams.solver.board;

import itba.eda.pipedreams.solver.algorithm.Solution;
import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.pipe.Pipe;

import java.util.Deque;

public class Board extends BasicBoard {
	private Tile[][] board;

	public Board(String[] tiles) {
		file = tiles.clone();
		board = new Tile[tiles.length][tiles[0].length()];

		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[0].length(); j++) {
				if(!setPiece(tiles[i].charAt(j), i, j)) {
					throw new IllegalArgumentException("Invalid board. Too many starting points or has invalid tile.");
				}
			}
		}

		if(startPoint == null) {
			throw new IllegalArgumentException("Invalid board. No starting point.");
		}
	}

	@Override
	protected boolean setPiece(char c, int row, int column) {
		Tile piece;
		c = Character.toUpperCase(c);

		switch(c) {
			case 'N':
			case 'S':
			case 'W':
			case 'E':
				if(startPoint != null) {
					return false;
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
				return false;
		}

		board[row][column] = piece;
		return true;
	}

	@Override
	public Pipe getPipe(Point point) {
		return board[point.getRow()][point.getColumn()].pipe;
	}

	@Override
	public boolean putPipe(Pipe pipe, Point point) {
		if(isEmpty(point)) { // TODO: Remove check?
			board[point.getRow()][point.getColumn()] = Tile.get(pipe);
			setChanged();
			return true;
		}

		return false;
	}

	@Override
	public boolean removePipe(Point point) {
		if(hasPipe(point)) {
			board[point.getRow()][point.getColumn()] = Tile.EMPTY;
			setChanged();
			return true;
		}

		return false;
	}

	@Override
	public boolean isEmpty(Point point) {
        if(!withinLimits(point)) {
            return false;
        }
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
	public String getRepresentation(Point point) {
		return board[point.getRow()][point.getColumn()].toString();
	}

	@Override
	public boolean draw(Deque<Pipe> pipes) {
		if(pipes == null || pipes.size() == 0) {
			return false;
		}

		Point point = getStartPoint();
		Dir flow = startFlow;
		point.next(flow);

		while(!pipes.isEmpty()) { // TODO: OK? & Errors?
			flow = flow.opposite();
			Tile tile = Tile.get(pipes.removeLast());
			board[point.getRow()][point.getColumn()] = tile;
			flow = tile.getPipe().flow(flow);
			point.next(flow);
		}

		setChanged();

		return true;
	}

	@Override
	public boolean draw(Solution pipes) {
		if(pipes == null || pipes.size() == 0) {
			return false;
		}

		Point point = getStartPoint();
		Dir flow = startFlow;
		point.next(flow);

		for(Pipe pipe : pipes) {
			flow = flow.opposite();
			Tile tile = Tile.get(pipe);
			board[point.getRow()][point.getColumn()] = tile;
			flow = pipe.flow(flow);
			point.next(flow);
		}

		setChanged();

		return true;
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

	private static enum Tile implements BasicTile {
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

		@Override
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
