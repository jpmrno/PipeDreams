package itba.eda.pipedreams.solver;

import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.board.Board;
import itba.eda.pipedreams.solver.engine.Engine;
import itba.eda.pipedreams.solver.pipe.PipeBox;

public class PipeDreamsSolver { // Front/Back end mediator

	private final GameDisplay display;
	private final PipeBox pipes;
	private final BasicBoard board;
	private final Engine engine;

	public PipeDreamsSolver(PDSolverArguments args, GameDisplay display) { // TODO: Create PDSolverArguments here?
		this.pipes = new PipeBox(args.getPipeSizes());
		this.board = new Board(args.getBoardFile());
		this.engine = new Engine(board, args.getMethod(), args.getTime(), pipes);
		this.display = display;
	}

	public void start() {
		engine.start();
	}
}
