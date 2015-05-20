package itba.eda.pipedreams.tablelogic;

public interface BasicGamePipe {
	boolean canFlow(Dir from);
	Dir flow(Dir from);
}