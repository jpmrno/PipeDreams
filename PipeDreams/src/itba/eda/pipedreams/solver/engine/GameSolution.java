package itba.eda.pipedreams.solver.engine;

import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.board.Dir;
import itba.eda.pipedreams.solver.pipe.Pipe;
import itba.eda.pipedreams.solver.pipe.PipeBox;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

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
		Point point = board.getStartPoint().getNext(board.getStartFlow());
		Dir flow = board.getStartFlow();
        ///////////////////////////DEBUG
        System.out.println("--- Finding NEW best neighbor ---");
        System.out.print("Current Solution: ");
        this.print();
        ///////////////////////////DEBUG
		for(Pipe pipe : this) {
			GameSolution solution = new GameSolution();

			flow = flow.opposite();

			int skip = Heuristics.apply(board, point.clone(), flow, pipeBox, solution); //TODO: Ver si clonar aca esta bien

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

        System.out.print("Partial Solution: ");
        prevSolution.print();

		i = 0;
        /////////////VILLA////////////////////////
        Deque<Pipe> firstPart = new LinkedList<>();
        Iterator<Pipe> it = pipes.descendingIterator();

        for(int j=0; j < solutionIndex; j++){
            firstPart.offer(it.next());
            i++;
        }

        Iterator<Pipe> auxIt = firstPart.descendingIterator();
        while(auxIt.hasNext()) { //SE ESTABAN AGREGANDO BIEN, PERO AL REVES, HAY QUE GUARDAR LOS INICIALES EN UN STACK E IR POPPEANDO
            Pipe pipe = auxIt.next();
            prevSolution.pipes.addLast(pipe);
        }
        /////////////VILLA////////////////////////
		while(it.hasNext()) {
			Pipe pipe = it.next();

            if(i >= solutionIndex && i < solutionIndex + prevSkip) {
				pipeBox.addOnePipe(pipe.ordinal());
			} else {
				prevSolution.pipes.addFirst(pipe);
			}
            i++;
		}

        System.out.print("Best Neighbor found: ");
        prevSolution.print();
        ///////////////////////////DEBUG

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

    private void print() {
        for(Pipe each : this) {
            System.out.print(each + " ");
        }
        System.out.println();
    }
}
