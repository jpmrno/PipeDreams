package itba.eda.pipedreams.solver.pipe;

import itba.eda.pipedreams.solver.board.Dir;

public interface BasicPipe {
	boolean canFlow(Dir from);
	Dir flow(Dir from);
}