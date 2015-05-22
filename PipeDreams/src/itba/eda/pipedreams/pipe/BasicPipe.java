package itba.eda.pipedreams.pipe;

import itba.eda.pipedreams.board.Dir;

public interface BasicPipe {
	boolean canFlow(Dir from);
	Dir flow(Dir from);
}