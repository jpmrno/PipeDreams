package itba.eda.pipedreams.enginelogic;

import itba.eda.pipedreams.Method;
import itba.eda.pipedreams.pipelogic.Pipe;
import itba.eda.pipedreams.pipelogic.PipeBox;
import itba.eda.pipedreams.tablelogic.Board;
import itba.eda.pipedreams.tablelogic.Dir;
import itba.eda.pipedreams.tablelogic.Point;

import java.util.Deque;
import java.util.LinkedList;

public class Engine {

	private Board board;
	private Method method;
	private PipeBox pipeBox;

	private Timer timer;

	private boolean iterative;

	//private Map<Pipe, Integer> pipeBox;

	public Engine(Board board, Method method, int[] sizes) {
		this.board = board;
		this.method = method;
		//this.pipeBox = new EnumMap<Pipe, Integer>(Pipe.class);
		pipeBox = new PipeBox(sizes);
	}

	public void start() {
		timer = new Timer();
		timer.startClock();
		switch(method) {
			case EXACT:
				backtracking();
				break;
		}
		timer.stopClock();
	}

	private void backtracking() {
		if(iterative) {

		} else {
			Deque<Pipe> longestPath = new LinkedList<Pipe>(); // TODO: Pipe + Point??
			Deque<Pipe> currPath = new LinkedList<Pipe>();
			backtrackingRec(Board.getNext(board.getStartPoint().clone(), board.getStartFlow()), board.getStartFlow(), currPath, longestPath);
			System.out.println(board);
		}
	}

	private void backtrackingRec(Point point, Dir to, Deque<Pipe> currentPath, Deque<Pipe> longestPath) {
		Dir from = to.opposite();

		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(board);

		if(!board.withinLimits(point)) {
			if(currentPath.size() > longestPath.size()) {
				System.out.println("Mejor solucion de: " + (currentPath.size() + 1));
				timer.printRunningTime();
				copyDeque(currentPath, longestPath); // TODO: Ask if this is the longest possible path, where?
			}
			return;
		}

		if(!board.isEmpty(point)) {
			if(!board.isBlocked(point, from)) {
				Pipe pipe = board.getPipe(point);
				currentPath.push(pipe);

				backtrackingRec(Board.getNext(point, pipe.flow(from)), pipe.flow(from), currentPath, longestPath);
				Board.getPrevious(point, pipe.flow(from));

				currentPath.pop();
			}
			return;
		}

		for(int i = 0; i < pipeBox.length(); i++) {
			Pipe pipe = pipeBox.getPipe(i);
			int size = pipeBox.getSize(i);
			Dir flow = pipe.flow(from);

			if(flow != null && size > 0) {
				pipeBox.removePipe(i);
				board.putPipe(pipe, point);
				currentPath.push(pipe);

				backtrackingRec(Board.getNext(point, pipe.flow(from)), pipe.flow(from), currentPath, longestPath);
				Board.getPrevious(point, pipe.flow(from));

				if(bestSolution(longestPath)) {
					return;
				}

				currentPath.pop();
				board.removePipe(point);
				pipeBox.addPipe(i);
			}
		}

	}

	private boolean bestSolution(Deque<Pipe> longestPath) {
		return longestPath.size() == pipeBox.getLongestPossible();
	}

	private <T> void copyDeque(Deque<T> from, Deque<T> to) { // TODO: Better way?
		while(!to.isEmpty()) {
			to.pop();
		}

		for(T aux : from) {
			to.add(aux);
		}
	}
}
