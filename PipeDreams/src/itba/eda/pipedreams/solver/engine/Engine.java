package itba.eda.pipedreams.solver.engine;

import itba.eda.pipedreams.solver.Method;
import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.board.Board;
import itba.eda.pipedreams.solver.board.Dir;
import itba.eda.pipedreams.solver.pipe.Pipe;
import itba.eda.pipedreams.solver.pipe.PipeBox;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Engine implements Runnable {
	private static final int DELAY = 100;

	private Board board; // TODO: Interfaces?
	private Method method;
	private long time;
	private PipeBox pipeBox; // TODO: Interfaces?

	private boolean withProgress;

	public Engine(Board board, Method method, int time, boolean withProgress, PipeBox pipeBox) {
		this.board = board;
		this.method = method;
		this.withProgress = withProgress;
		this.time = Timer.convertToMiliseconds(time);
		this.pipeBox = pipeBox;
	}

	@Override
	public void run() {
		switch(method) {
			case EXACT:
				try {
					backtracking(); // TODO: Que hacer con las exceptions
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case APROX:
                try {
                    hill();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
		}
	}

	private void backtracking() throws InterruptedException {
		Timer timer = new Timer();
        timer.startClock();

        Deque<Pipe> longestPath = new LinkedList<Pipe>();
        Deque<Pipe> currPath = new LinkedList<Pipe>();
        backtrackingRec(BasicBoard.getNext(board.getStartPoint().clone(), board.getStartFlow()), board.getStartFlow(), currPath, longestPath);
        board.draw(longestPath);
        board.notifyObservers();

		timer.stopClock(); //TODO Preguntar si deberia ir en start()
	}

	private void backtrackingRec(Point point, Dir to, Deque<Pipe> currentPath, Deque<Pipe> longestPath) throws InterruptedException {
		Dir from = to.opposite();

		if(withProgress) { // TODO: OK here?
			board.notifyObservers();
			Thread.sleep(DELAY);
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

    private <T> void copyQueue(Queue<T> from, Queue<T> to) { // TODO: Better way?
        to.clear();

        for(T aux : from) {
            to.add(aux);
        }
    }

	private boolean bestSolution(Deque<Pipe> longestPath) {
		return longestPath.size() == pipeBox.getLongestPossible();
	}

	private void hill() throws InterruptedException { // TODO: Change name
		Timer timer = new Timer();
		timer.startClock();

		Solution bestSolution = null;
		Solution localSolution;
        PipeBox initialPipeBox = pipeBox.clone();
		boolean betterFound;

		while(timer.getRunningTime() < time) {
			board.clear();
            pipeBox = initialPipeBox.clone();

			localSolution = randomSolution();

			if(withProgress) {
				board.notifyObservers();
				Thread.sleep(DELAY);
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
						Thread.sleep(DELAY);
					}
				}
			} while(betterFound && timer.getRunningTime() < time);

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

        if(randomSolutionRec(board.getStartPoint().getNext(board.getStartFlow()), board.getStartFlow(), solution, mapPipeBox)) {
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

				if(randomSolutionRec(BasicBoard.getNext(point, pipe.flow(from)), pipe.flow(from), solution, mapPipeBox)) {
                    return true;
                }
				BasicBoard.getPrevious(point, pipe.flow(from));
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

				if(randomSolutionRec(BasicBoard.getNext(point, pipe.flow(from)), pipe.flow(from), solution, mapPipeBox)) {
					return true;
				}
				BasicBoard.getPrevious(point, pipe.flow(from));

				solution.remove();
				board.removePipe(point);
				pipeBox.addOnePipe(mapPipeBox[i]);
			}
		}
		return false;
	}

}
