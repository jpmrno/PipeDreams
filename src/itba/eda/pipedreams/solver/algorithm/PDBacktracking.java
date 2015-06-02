package itba.eda.pipedreams.solver.algorithm;

import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.board.Dir;
import itba.eda.pipedreams.solver.engine.Engine;
import itba.eda.pipedreams.solver.pipe.Pipe;
import itba.eda.pipedreams.solver.pipe.PipeBox;

public class PDBacktracking implements Algorithm {
	private final BasicBoard board;
	private final boolean withProgress;
	private final PipeBox pipeBox;

	public PDBacktracking(BasicBoard board, PipeBox pipeBox, boolean withProgress) {
		this.board = board;
		this.pipeBox = pipeBox;
		this.withProgress = withProgress;
	}

	@Override
	public int solve() throws InterruptedException {
		Solution longestPath = new Solution();
		Solution currPath = new Solution();
		backtrackingRec(board.getStartPoint().next(board.getStartFlow()), board.getStartFlow(), currPath, longestPath);

		System.out.println(longestPath);
		board.draw(longestPath.descendingIterator());
		board.notifyObservers();

		return longestPath.size() + 1;
	}

	private void backtrackingRec(Point point, Dir to, Solution currentPath, Solution longestPath) throws InterruptedException {
		Dir from = to.opposite();

		if(withProgress) {
			board.notifyObservers();
			Thread.sleep(Engine.DELAY);
		}

		if(!board.withinLimits(point)) {
			if(currentPath.size() > longestPath.size()) {
				Solution.copy(currentPath, longestPath);
			}

			return;
		}

		if(!board.isEmpty(point)) {
			if(!board.isBlocked(point, from)) {
				Pipe pipe = board.getPipe(point);
				currentPath.add(pipe);

				backtrackingRec(point.next(pipe.flow(from)), pipe.flow(from), currentPath, longestPath);
				point.previous(pipe.flow(from));

				currentPath.remove();
			}

			return;
		}

		for(int i = 0; i < pipeBox.length(); i++) {
			Pipe pipe = pipeBox.getPipe(i);
			int size = pipeBox.getSize(pipe);
			Dir flow = pipe.flow(from);

			if(flow != null && size > 0) {
				pipeBox.removeOnePipe(pipe);
				board.putPipe(pipe, point);
				currentPath.add(pipe);

				backtrackingRec(point.next(pipe.flow(from)), pipe.flow(from), currentPath, longestPath);
				point.previous(pipe.flow(from));

				if(bestSolution(longestPath)) {
					return;
				}

				currentPath.remove();
				board.removePipe(point);
				pipeBox.addOnePipe(pipe);
			}
		}
	}

	private boolean bestSolution(Solution longestPath) {
		return longestPath.size() == pipeBox.getLongestPossible();
	}
}
