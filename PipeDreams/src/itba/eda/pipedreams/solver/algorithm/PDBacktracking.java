package itba.eda.pipedreams.solver.algorithm;

import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.board.Dir;
import itba.eda.pipedreams.solver.engine.Engine;
import itba.eda.pipedreams.solver.engine.Timer;
import itba.eda.pipedreams.solver.pipe.Pipe;
import itba.eda.pipedreams.solver.pipe.PipeBox;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class PDBacktracking implements Algorithm {
	private BasicBoard board;
	private boolean withProgress;
	private PipeBox pipeBox;

	public PDBacktracking(BasicBoard board, PipeBox pipeBox, boolean withProgress) {
		this.board = board;
		this.pipeBox = pipeBox;
		this.withProgress = withProgress;
	}

	@Override
	public void solve() throws InterruptedException {
		Timer timer = new Timer();
		timer.startClock();

		Deque<Pipe> longestPath = new LinkedList<Pipe>();
		Deque<Pipe> currPath = new LinkedList<Pipe>();
		backtrackingRec(board.getStartPoint().next(board.getStartFlow()), board.getStartFlow(), currPath, longestPath);
		board.draw(longestPath);
		board.notifyObservers();

		timer.stopClock(); //TODO Preguntar si deberia ir en start()
	}

	private void backtrackingRec(Point point, Dir to, Deque<Pipe> currentPath, Deque<Pipe> longestPath) throws InterruptedException {
		Dir from = to.opposite();

		if(withProgress) { // TODO: OK here?
			board.notifyObservers();
			Thread.sleep(Engine.DELAY);
		}

		if(!board.withinLimits(point)) {
			if(currentPath.size() > longestPath.size()) {
				copyQueue(currentPath, longestPath);
			}
			return;
		}

		if(!board.isEmpty(point)) {
			if(!board.isBlocked(point, from)) {
				Pipe pipe = board.getPipe(point);
				currentPath.push(pipe);

				backtrackingRec(point.next(pipe.flow(from)), pipe.flow(from), currentPath, longestPath);
				point.next(pipe.flow(from));

				currentPath.pop();
			}
			return;
		}

		for(int i = 0; i < pipeBox.length(); i++) {
			Pipe pipe = pipeBox.getPipe(i);
			int size = pipeBox.getSize(i);
			Dir flow = pipe.flow(from);

			if(flow != null && size > 0) {
				pipeBox.removeOnePipe(i);
				board.putPipe(pipe, point);
				currentPath.push(pipe);

				backtrackingRec(point.next(pipe.flow(from)), pipe.flow(from), currentPath, longestPath);
				point.previous(pipe.flow(from));

				if(bestSolution(longestPath)) {
					return;
				}

				currentPath.pop();
				board.removePipe(point);
				pipeBox.addOnePipe(i);
			}
		}
	}

	private boolean bestSolution(Deque<Pipe> longestPath) {
		return longestPath.size() == pipeBox.getLongestPossible();
	}

	private <T> void copyQueue(Queue<T> from, Queue<T> to) { // TODO: Better way?
		to.clear();

		for(T aux : from) {
			to.add(aux);
		}
	}
}
