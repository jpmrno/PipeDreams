package itba.eda.pipedreams.solver.algorithm;

import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.board.Dir;
import itba.eda.pipedreams.solver.engine.Engine;
import itba.eda.pipedreams.solver.engine.Timer;
import itba.eda.pipedreams.solver.pipe.Pipe;
import itba.eda.pipedreams.solver.pipe.PipeBox;

public class PDHillClimbing implements Algorithm {
	private PipeBox pipeBox;
	private final int runningTime;
	private final BasicBoard board;
	private final boolean withProgress;

	public PDHillClimbing(BasicBoard board, PipeBox pipeBox, int time, boolean withProgress) {
		this.board = board;
		this.pipeBox = pipeBox;
		this.runningTime = Timer.convertToMiliseconds(time);
		this.withProgress = withProgress;
	}

	@Override
	public void solve() throws InterruptedException {
		Timer timer = new Timer();
		timer.startClock();

		Solution bestSolution = null;
		Solution localSolution;
		PipeBox initialPipeBox = pipeBox.clone();
		boolean betterFound;

		while(timer.getRunningTime() < runningTime) {
			board.clear();
			pipeBox = initialPipeBox.clone();

			localSolution = randomSolution();

			if(withProgress) {
				board.notifyObservers();
				Thread.sleep(Engine.DELAY);
			}

			if(localSolution == null) {
				timer.stopClock();
				System.out.println("No solution found.");
				return;
			}

			do {
				betterFound = false;
				Solution solution = localSolution.bestNeighbor(board, pipeBox);

				if(solution.compareTo(localSolution) > 0) {
					localSolution = solution;
					betterFound = true;

					board.draw(localSolution);

					if(withProgress) {
						board.notifyObservers();
						Thread.sleep(Engine.DELAY);
					}
				}
			} while(betterFound && timer.getRunningTime() < runningTime);

			if(bestSolution == null || localSolution.compareTo(bestSolution) > 0) {
				bestSolution = localSolution;
			}
		}
		timer.stopClock();

		board.clear();
		board.draw(bestSolution);
		board.notifyObservers();
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
			int size = pipeBox.getSize(mapPipeBox[i]);
			Dir flow = pipe.flow(from);

			if(flow != null && size > 0) {
				pipeBox.removeOnePipe(mapPipeBox[i]);
				board.putPipe(pipe, point);
				solution.add(pipe);

				if(randomSolutionRec(point.next(pipe.flow(from)), pipe.flow(from), solution, mapPipeBox)) {
					return true;
				}
				point.previous(pipe.flow(from));

				solution.remove();
				board.removePipe(point);
				pipeBox.addOnePipe(mapPipeBox[i]);
			}
		}
		return false;
	}
}
