package itba.eda.pipedreams.solver.engine;

public interface Solution {
	boolean isApplicable(BasicBoard board, Point point);

	void applySolution(BasicBoard board, Point point);
}
