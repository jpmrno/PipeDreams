package itba.eda.pipedreams.solver.basic;

import itba.eda.pipedreams.solver.board.Dir;

public class Point implements Cloneable {
    private int row;
    private int column;

    public Point(int row, int column) {
        this.row = row;
        this.column = column;
    }

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

    public Point next(Dir to) {
		switch(to) {
			case NORTH:
				row--;
				break;
			case SOUTH:
				row++;
				break;
			case WEST:
				column--;
				break;
			case EAST:
				column++;
				break;
			default:
				throw new IllegalStateException();
		}

		return this;
    }

	public Point previous(Dir to) {
		return next(to.opposite());
	}

	public static Point getNext(Point point, Dir to) {
		switch(to) {
			case NORTH:
				point = new Point(point.row - 1, point.column);
				break;
			case SOUTH:
				point = new Point(point.row + 1, point.column);
				break;
			case WEST:
				point = new Point(point.row, point.column - 1);
				break;
			case EAST:
				point = new Point(point.row, point.column + 1);
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
	public Point clone() {
		return new Point(row, column);
	}
}
