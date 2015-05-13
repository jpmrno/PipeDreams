package itba.eda.pipedreams.pipelogic;

import itba.eda.pipedreams.tablelogic.Dir;

public interface Pipe {
	boolean canFlow(Dir from);
	
	Dir flow(Dir from);
}