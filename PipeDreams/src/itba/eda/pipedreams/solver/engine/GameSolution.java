package itba.eda.pipedreams.solver.engine;

import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.board.Dir;
import itba.eda.pipedreams.solver.pipe.Pipe;
import itba.eda.pipedreams.solver.pipe.PipeBox;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class GameSolution implements Iterable<Pipe>, Comparable<GameSolution> {
	private Deque<Pipe> pipes;

	public GameSolution() {
		pipes = new LinkedList<>();
	}

	public void add(Pipe pipe) {
		pipes.push(pipe);
	}

	public Pipe remove() {
		return pipes.pop();
	}

	public int size() {
		return pipes.size();
	}

	public GameSolution bestNeighbor(BasicBoard board, PipeBox pipeBox) { // TODO: PartialSolution class?
		GameSolution prevSolution = new GameSolution();
		int prevSkip = 0, solutionIndex = -1, i = 0;
		Point point = BasicBoard.getNext(board.getStartPoint(), board.getStartFlow());
		Dir flow = board.getStartFlow();

		for(Pipe pipe : this) {
			GameSolution solution = new GameSolution();

			flow = flow.opposite();

			int skip = Heuristics.apply(board, point, flow, pipeBox, solution);

			if(solution.size() - skip > prevSolution.size() - prevSkip) {
				prevSkip = skip;
				prevSolution = solution;
				solutionIndex = i;
			}

			flow = pipe.flow(flow);
			BasicBoard.getNext(point, flow);
			i++;
		}

		if(solutionIndex == -1) {
			return this; // TODO: Que hacer cuando soy la mejor?
		}

		for(Pipe pipe : prevSolution) {
			pipeBox.removeOnePipe(pipe.ordinal());
		}

		i = 0;
		for(Pipe pipe : this) {
			if(i < solutionIndex) {
				prevSolution.pipes.addLast(pipe);
			} else if(i >= solutionIndex && i < solutionIndex + prevSkip) {
				pipeBox.addOnePipe(pipe.ordinal());
			} else {
				prevSolution.pipes.addFirst(pipe);
			}
		}



		return prevSolution;
	}

	@Override
	public Iterator<Pipe> iterator() {
		return pipes.descendingIterator();
	}

	@Override
	public int compareTo(GameSolution o) {
		return pipes.size() - o.pipes.size();
	}
}
