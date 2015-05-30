package itba.eda.pipedreams.solver.basic;

import itba.eda.pipedreams.solver.board.BasicBoard;

import java.util.Observer;

public interface BoardDisplay extends Observer {
	void setBoard(BasicBoard board);
	void saveAsPng();
}
