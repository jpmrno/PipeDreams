package itba.eda.pipedreams.tablelogic;

public class Point {
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

	public Point clone() {
		return new Point(row, column);
	}

	@Override
	public String toString() {
		return "Point{" +
				"row=" + row +
				", column=" + column +
				'}';
	}
}
