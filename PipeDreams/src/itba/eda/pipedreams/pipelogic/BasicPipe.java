package itba.eda.pipedreams.pipelogic;

import itba.eda.pipedreams.tablelogic.Dir;

public interface BasicPipe {
	boolean canFlow(Dir from);
	Dir flow(Dir from);
}