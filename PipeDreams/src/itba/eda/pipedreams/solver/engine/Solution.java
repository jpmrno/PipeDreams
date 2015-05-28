package itba.eda.pipedreams.solver.engine;

import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.basic.Point;

public interface Solution {
	boolean isApplicable(BasicBoard board, Point point);

	void applySolution(BasicBoard board, Point point);
}
