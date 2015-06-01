package itba.eda.pipedreams.solver.basic;

import itba.eda.pipedreams.solver.board.Dir;

public class Point {
    private int row;
    private int column;

    public Point(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Point getNext(Dir to)
    {
        Point point;
        switch(to) {
            case NORTH:
                point = new Point(row - 1, column);
                break;
            case SOUTH:
                point = new Point(row + 1, column);
                break;
            case WEST:
                point = new Point(row, column - 1);
                break;
            case EAST:
                point = new Point(row, column + 1);
                break;
            default:
                throw new IllegalStateException();
        }
        return point;
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

	public Point clone() {
		return new Point(row, column);
	}

    public Point goN() {
        return new Point(row - 1, column);
    }

    public Point goW() {
        return new Point(row, column - 1);
    }

    public Point goS() {
        return new Point(row + 1, column);
    }

    public Point goE() {
        return new Point(row, column + 1);
    }

    public Point goNE() {
        return new Point(row -1 , column + 1);
    }

    public Point goNW() { return new Point(row -1 , column - 1); }

    public Point goSW() {
        return new Point(row + 1, column - 1);
    }

    public Point goSE() {
        return new Point(row + 1, column + 1);
    }

	@Override
	public String toString() {
		return "Point{" +
				"row=" + row +
				", column=" + column +
				'}';
	}
}
