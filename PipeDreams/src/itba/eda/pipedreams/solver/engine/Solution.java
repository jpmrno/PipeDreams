package itba.eda.pipedreams.solver.engine;

import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.board.Dir;
import itba.eda.pipedreams.solver.pipe.Pipe;
import itba.eda.pipedreams.solver.pipe.PipeBox;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class Solution implements Iterable<Pipe>, Comparable<Solution> {
	private Deque<Pipe> pipes;

	public Solution() {
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

	public Solution bestNeighbor(BasicBoard board, PipeBox pipeBox) { // TODO: PartialSolution class?
		Solution prevSolution = new Solution();
		int prevSkip = 0, solutionIndex = -1, i = 0;
		Point point = board.getStartPoint().getNext(board.getStartFlow());
		Dir flow = board.getStartFlow();

		for(Pipe pipe : this) {
			Solution solution = new Solution();

			flow = flow.opposite();

			int skip = Heuristics.apply(board, point.clone(), flow, pipeBox, solution); // TODO: Ver si clonar aca esta bien

			if(solution.size() - skip > prevSolution.size() - prevSkip) {
				prevSkip = skip;
				prevSolution = solution;
				solutionIndex = i;
			}

			flow = pipe.flow(flow);
			point = point.getNext(flow);
			i++;
		}

		if(solutionIndex == -1) {
			return this; // TODO: Que hacer cuando soy la mejor?
		}

		for(Pipe pipe : prevSolution) {
			pipeBox.removeOnePipe(pipe.ordinal());
		}

        Deque<Pipe> firstPart = new LinkedList<>();
        Iterator<Pipe> it = pipes.descendingIterator();
        for(i = 0; i < solutionIndex; i++) { // TODO: FEO?
            firstPart.offer(it.next());
        }

        Iterator<Pipe> auxIt = firstPart.descendingIterator();
        while(auxIt.hasNext()) {
            prevSolution.pipes.addLast(auxIt.next());
        }

		while(it.hasNext()) {
			Pipe pipe = it.next();

            if(i < solutionIndex + prevSkip) {
				pipeBox.addOnePipe(pipe.ordinal());
			} else {
				prevSolution.pipes.addFirst(pipe);
			}

            i++;
		}

		return prevSolution;
	}

	@Override
	public Iterator<Pipe> iterator() {
		return pipes.descendingIterator();
	}

	@Override
	public int compareTo(Solution o) {
		return pipes.size() - o.pipes.size();
	}

    private void print(String which) { // TODO: DEBUGGING ONLY
        for(Pipe each : this) {
            System.out.print(each + " ");
        }
        System.out.println();
    }
}
