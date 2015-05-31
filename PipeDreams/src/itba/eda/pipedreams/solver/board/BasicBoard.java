package itba.eda.pipedreams.solver.board;

import itba.eda.pipedreams.solver.basic.GameBoard;
import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.pipe.Pipe;

public interface BasicBoard extends GameBoard {
	Pipe getPipe(Point point);
	boolean putPipe(Pipe pipe, Point point);
	boolean removePipe(Point point);

	boolean isEmpty(Point point);
	boolean hasPipe(Point point);
	boolean isBlocked(Point point, Dir from);

	boolean withinLimits(Point point);

	Point getStartPoint();
	Dir getStartFlow();

	static Point getNext(Point point, Dir dir) {
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

	static Point getPrevious(Point point, Dir dir) {
		return getNext(point, dir.opposite());
	}
}
