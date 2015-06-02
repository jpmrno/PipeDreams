package itba.eda.pipedreams.solver.pipe;

import itba.eda.pipedreams.solver.board.Dir;

public interface BasicPipe {
	Dir flow(Dir from);
}