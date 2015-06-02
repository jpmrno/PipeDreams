package itba.eda.pipedreams.solver.algorithm;

import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.board.Dir;
import itba.eda.pipedreams.solver.pipe.BasicPipeBox;
import itba.eda.pipedreams.solver.pipe.Pipe;
import itba.eda.pipedreams.solver.pipe.PipeBox;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class Solution implements Iterable<Pipe>, Comparable<Solution> {
	private final Deque<Pipe> pipes;

	public Solution() {
		pipes = new LinkedList<Pipe>();
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

	public Solution bestNeighbor(BasicBoard board, PipeBox pipeBox) { // TODO: PartialSolution class?
		Solution prevSolution = new Solution();
		int prevSkip = 0, solutionIndex = -1, i = 0;

		Point point = board.getStartPoint();
		Dir flow = board.getStartFlow();
		point.next(flow);

		for(Pipe pipe : this) {
			Solution solution = new Solution();

			flow = flow.opposite();

			int skip = Heuristics.apply(board, point.clone(), flow, pipeBox, solution);

			if(solution.size() - skip > prevSolution.size() - prevSkip) {
				prevSkip = skip;
				prevSolution = solution;
				solutionIndex = i;
			}

			flow = pipe.flow(flow);
			point.next(flow);
			i++;
		}

		if(solutionIndex == -1) {
			return this; // TODO: Que hacer cuando soy la mejor?
		}

		for(Pipe pipe : prevSolution) {
			pipeBox.removeOnePipe(pipe);
		}

        concat(prevSolution, this, solutionIndex, pipeBox, prevSkip);

		return prevSolution;
	}

	private static void concat(Solution ret, Solution sol, int index, BasicPipeBox pipeBox, int toAdd) {
		Deque<Pipe> firstPart = new LinkedList<Pipe>();
		Iterator<Pipe> it = sol.pipes.descendingIterator();
		int i = 0;
		for(; i < index; i++) {
			firstPart.offer(it.next());
		}

		Iterator<Pipe> auxIt = firstPart.descendingIterator();
		while(auxIt.hasNext()) {
			ret.pipes.addLast(auxIt.next());
		}

		while(it.hasNext()) {
			Pipe pipe = it.next();

			if(i < index + toAdd) {
				pipeBox.addOnePipe(pipe);
			} else {
				ret.pipes.addFirst(pipe);
			}

			i++;
		}
	}

	@Override
	public Iterator<Pipe> iterator() {
		return pipes.descendingIterator();
	}

	@Override
	public int compareTo(Solution o) {
		return pipes.size() - o.pipes.size();
	}
}
