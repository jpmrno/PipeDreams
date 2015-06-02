package itba.eda.pipedreams.solver.algorithm;

import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.board.Dir;
import itba.eda.pipedreams.solver.engine.Engine;
import itba.eda.pipedreams.solver.engine.Timer;
import itba.eda.pipedreams.solver.pipe.Pipe;
import itba.eda.pipedreams.solver.pipe.PipeBox;

public class PDHillClimbing implements Algorithm {
	private Timer timer;
	private final int runningTime;

	private PipeBox pipeBox;
	private final BasicBoard board;
	private final boolean withProgress;

	public PDHillClimbing(BasicBoard board, PipeBox pipeBox, int time, boolean withProgress) {
		this.board = board;
		this.pipeBox = pipeBox;
		this.runningTime = Timer.convertToMilliseconds(time);
		this.withProgress = withProgress;
	}

	@Override
	public int solve() throws InterruptedException {
		timer = new Timer();
		timer.start();

		Solution bestSolution = null;
		Solution localSolution;
		PipeBox initialPipeBox = pipeBox.clone();
		boolean betterFound;

		while(hasTime()) {
			board.clear();
			pipeBox = initialPipeBox.clone();

			localSolution = randomSolution();

			if(localSolution != null) {
				if(withProgress) {
					board.notifyObservers();
					Thread.sleep(Engine.DELAY);
				}

				do {
					betterFound = false;
					Solution solution = localSolution.bestNeighbor(board, pipeBox);

					if(solution.compareTo(localSolution) > 0) {
						localSolution = solution;
						betterFound = true;

						board.draw(localSolution.iterator());

						if(withProgress) {
							board.notifyObservers();
							Thread.sleep(Engine.DELAY);
						}
					}
				} while(betterFound && hasTime());

				if(bestSolution == null || localSolution.compareTo(bestSolution) > 0) {
					bestSolution = localSolution;
				}
			}
		}
		timer.stop();

		if(bestSolution == null) {
			return 0;
		}

		board.clear();
		board.draw(bestSolution.iterator());
		board.notifyObservers();

		return bestSolution.size() + 1;
	}

	private Solution randomSolution() {
		Solution solution = new Solution();
		int[] mapPipeBox = PipeBox.shufflePipes();

		if(randomSolutionRec(board.getStartPoint().next(board.getStartFlow()), board.getStartFlow(), solution, mapPipeBox)) {
			return solution;
		}

		return null;
	}

	private boolean randomSolutionRec(Point point, Dir to, Solution solution, int[] mapPipeBox) {
		Dir from = to.opposite();

		if(!board.withinLimits(point)) {
			return true;
		}

		if(!board.isEmpty(point)) {
			if(!board.isBlocked(point, from)) {
				Pipe pipe = board.getPipe(point);
				solution.add(pipe);

				if(randomSolutionRec(point.next(pipe.flow(from)), pipe.flow(from), solution, mapPipeBox)) {
					return true;
				}
				point.previous(pipe.flow(from));

				solution.remove();
			}

			return false;
		}

		for(int i = 0; i < pipeBox.length(); i++) {
			Pipe pipe = pipeBox.getPipe(mapPipeBox[i]);
			int size = pipeBox.getSize(pipe);
			Dir flow = pipe.flow(from);

			if(flow != null && size > 0) {
				pipeBox.removeOnePipe(pipe);
				board.putPipe(pipe, point);
				solution.add(pipe);

				if(randomSolutionRec(point.next(pipe.flow(from)), pipe.flow(from), solution, mapPipeBox)) {
					return true;
				}
				point.previous(pipe.flow(from));

				solution.remove();
				board.removePipe(point);
				pipeBox.addOnePipe(pipe);
			}
		}

		return false;
	}

	private boolean bestSolution(Solution longestPath) {
		return longestPath.size() == pipeBox.getLongestPossible();
	}

	private boolean hasTime() {
		return timer.getRunningTime() < runningTime;
	}
}
