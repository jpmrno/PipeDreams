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
		Timer timer = new Timer();
		timer.start();

		int longest = 0;
		try {
			longest = algorithm.solve();
		} catch(InterruptedException e) {
			e.printStackTrace(); // TODO: ?
		}
		timer.stop();

		if(longest == 0) {
			System.out.println("No solution found.");
		} else {
			System.out.println("Longest path found: " + longest);
		}
		timer.printRunningTime();
	}
}
