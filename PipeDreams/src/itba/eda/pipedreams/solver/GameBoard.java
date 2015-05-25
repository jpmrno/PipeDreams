package itba.eda.pipedreams.solver;

import itba.eda.pipedreams.solver.board.Point;

public interface GameBoard {
	int getRowSize();
	int getColumnSize();
	String getRepresentation(Point point);
}
