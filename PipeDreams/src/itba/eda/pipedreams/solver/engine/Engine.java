package itba.eda.pipedreams.solver.engine;

import itba.eda.pipedreams.solver.basic.BoardDisplay;
import itba.eda.pipedreams.solver.basic.Method;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.pipe.Pipe;
import itba.eda.pipedreams.solver.pipe.PipeBox;
import itba.eda.pipedreams.solver.board.Board;
import itba.eda.pipedreams.solver.board.Dir;
import itba.eda.pipedreams.solver.basic.Point;
import javafx.application.Platform;

import java.util.*;

public class Engine implements Runnable {
	private static final int DELAY = 500;

	private Board board; // TODO: Interfaces?
	private Method method;
	private long time;
	private PipeBox pipeBox; // TODO: Interfaces?

	private boolean withProgress;
	private boolean iterative;


	BoardDisplay display;



	public Engine(Board board, Method method, int time, boolean withProgress, PipeBox pipeBox) {
		this.board = board;
		this.method = method;
		this.withProgress = withProgress;
		this.time = Timer.convertToMiliseconds(time);
		this.pipeBox = pipeBox;
	}






	public Engine(Board board, Method method, int time, boolean withProgress, PipeBox pipeBox, BoardDisplay display) {
		this.board = board;
		this.method = method;
		this.withProgress = withProgress;
		this.time = Timer.convertToMiliseconds(time);
		this.pipeBox = pipeBox;
		this.display = display;
	}





	@Override
	public void run() {
		switch(method) {
			case EXACT:
				try {
					backtracking();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case APROX:
				hillClimbing();
				break;
		}
	}

	private Deque<Pipe> hillClimbing() { // TODO: Add notifies!
		Deque<Pipe> currSolution = new LinkedList<Pipe>();
		Deque<Pipe> bestSolution = new LinkedList<Pipe>();
		Timer timer = new Timer();

		timer.startClock();

		if(!findFirstSolution(BasicBoard.getNext(board.getStartPoint().clone(), board.getStartFlow()), board.getStartFlow(), currSolution)) {
			return null;
		}

		copyQueue(currSolution, bestSolution);

		while(timer.getRunningTime() < time) {
			currSolution = findBestNeighbor(currSolution);

			if(currSolution.size() > bestSolution.size()) { // TODO: currSolution can be null!
				copyQueue(currSolution, bestSolution);
			} else {
				return currSolution;
			}
		}
		timer.stopClock();

		return bestSolution;
	}

	private Deque<Pipe> findBestNeighbor(Deque<Pipe> currSolution) {
//		Deque<Point> bestNeighbor = new LinkedList<Point>();
//
//		for(Point currPos : currSolution) {
//			if(Solution.isApplicable(board, currPos)) {
//				setPossibleSolution(board, currPos);
//			}
//		}

		return null;
	}

	private void backtracking() throws InterruptedException {
		Timer timer = new Timer();
		timer.startClock();
		if(iterative) {

		} else {
			Deque<Pipe> longestPath = new LinkedList<Pipe>();
			Deque<Pipe> currPath = new LinkedList<Pipe>();
			backtrackingRec(BasicBoard.getNext(board.getStartPoint().clone(), board.getStartFlow()), board.getStartFlow(), currPath, longestPath);
			board.draw(longestPath);
			board.notifyObservers();
		}

		timer.stopClock(); //TODO Preguntar si deberia ir en start()
	}

	private void backtrackingRec(Point point, Dir to, Deque<Pipe> currentPath, Deque<Pipe> longestPath) throws InterruptedException {
		Dir from = to.opposite();

		if(withProgress) { // TODO: OK here?
			board.notifyObservers();
			Thread.sleep(DELAY);
		}

		if(!board.withinLimits(point)) {
			Platform.runLater(display::saveAsPng);
			if(currentPath.size() > longestPath.size()) {
				copyQueue(currentPath, longestPath);
			}
			return;
		}

		if(!board.isEmpty(point)) {
			if(!board.isBlocked(point, from)) {
				Pipe pipe = board.getPipe(point);
				currentPath.push(pipe);

				backtrackingRec(BasicBoard.getNext(point, pipe.flow(from)), pipe.flow(from), currentPath, longestPath);
				BasicBoard.getPrevious(point, pipe.flow(from));

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

				backtrackingRec(BasicBoard.getNext(point, pipe.flow(from)), pipe.flow(from), currentPath, longestPath);
				BasicBoard.getPrevious(point, pipe.flow(from));

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

	private boolean findFirstSolution(Point point, Dir to, Deque<Pipe> currentPath) {
		Dir from = to.opposite();

		System.out.println(board);

		if(!board.withinLimits(point)) {
			return true;
		}

		if(!board.isEmpty(point)) {
			if(!board.isBlocked(point, from)) {
				Pipe pipe = board.getPipe(point);
				currentPath.push(pipe);

				if(findFirstSolution(BasicBoard.getNext(point, pipe.flow(from)), pipe.flow(from), currentPath)) {
					return true;
				}
				BasicBoard.getPrevious(point, pipe.flow(from));

				currentPath.pop();
			}
			return false;
		}

		for(int i = 0; i < pipeBox.length(); i++) {
			Pipe pipe = pipeBox.getPipe(i);
			int size = pipeBox.getSize(i);
			Dir flow = pipe.flow(from);

			if(flow != null && size > 0) {
				pipeBox.removeOnePipe(i);
				board.putPipe(pipe, point);

				currentPath.push(pipe);

				if(findFirstSolution(BasicBoard.getNext(point, pipe.flow(from)), pipe.flow(from), currentPath)) {
					return true;
				}
				BasicBoard.getPrevious(point, pipe.flow(from));

				currentPath.pop();
				board.removePipe(point);
				pipeBox.addOnePipe(i);
			}
		}
		return false;
	}

	private <T> void copyQueue(Queue<T> from, Queue<T> to) { // TODO: Better way?
		to.clear();

		for(T aux : from) {
			to.add(aux);
		}
	}
}
