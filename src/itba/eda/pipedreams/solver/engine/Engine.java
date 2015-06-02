package itba.eda.pipedreams.solver.engine;

import itba.eda.pipedreams.solver.Method;
import itba.eda.pipedreams.solver.algorithm.Algorithm;
import itba.eda.pipedreams.solver.algorithm.PDBacktracking;
import itba.eda.pipedreams.solver.algorithm.PDHillClimbing;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.pipe.PipeBox;

public class Engine implements Runnable {
	public static final int DELAY = 100;

	private Algorithm algorithm;

	public Engine(BasicBoard board, Method method, int time, boolean withProgress, PipeBox pipeBox) {
		switch(method) {
			case EXACT:
				algorithm = new PDBacktracking(board, pipeBox, withProgress);
				break;
			case APROX:
				algorithm = new PDHillClimbing(board, pipeBox, time, withProgress);
				break;
		}
	}

	@Override
	public void run() {
		try {
			algorithm.solve();
		} catch(InterruptedException e) {
			e.printStackTrace(); // TODO: ?
		}
	}
}
