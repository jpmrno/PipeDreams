package itba.eda.pipedreams.solver.engine;

import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.board.Board;
import itba.eda.pipedreams.solver.board.Dir;
import itba.eda.pipedreams.solver.pipe.Pipe;
import itba.eda.pipedreams.solver.pipe.PipeBox;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class Solution implements Iterable<Pipe> {
	private Deque<Pipe> solution;
	private int[] aux;

	public Solution() {
		solution = new LinkedList<>();
	}

	public void applySolution(Board board) {
        Dir to = board.getStartFlow();
        Point currPoint = BasicBoard.getNext(board.getStartPoint(), to);
//        System.out.println("X= " + currPoint.getRow() + "Y= " + currPoint.getColumn());
        Pipe currPipe;

		while(!solution.isEmpty()) {
//            System.out.println("___________________While_____________________");
//            System.out.println("Este punto es X= " + currPoint.getRow() + "Y= " + currPoint.getColumn());
            board.removePipe(currPoint);
//            board.print();
            currPipe = solution.removeLast();
//            System.out.println(currPipe);
            board.putPipe(currPipe, currPoint);
//            board.print();
            to = currPipe.flow(to.opposite());
//            System.out.println("Moverse hacia: "+ to);
            currPoint = BasicBoard.getNext(currPoint, to);
//            System.out.println("El nuevo punto debe ser: X= " + currPoint.getRow() + "Y= " + currPoint.getColumn());

//            System.out.println(currPipe);
//            System.out.print("La cola es:");
//            for (Pipe each : solution) {
//                System.out.print(each +" - " );
//            }
//            System.out.println();

		}

        System.out.println("Solucion aplicada");
	}

	public void setPipe(Pipe pipe) {
		solution.addFirst(pipe);
	}

	public int size() {
		return solution.size();
	}

	public void push(Pipe pipe) {
		solution.push(pipe);
	}

	public Pipe pop() {
		return solution.pop();
	}

	public Solution cloneSol() {
		Solution newSol = new Solution();
		for(Pipe each : this) {
			newSol.push(each);
		}
        if(aux != null) {
            newSol.aux = new int[aux.length];
            for(int i=0; i < aux.length; i++) {
                newSol.aux[i] = aux[i];
            }
        }
        return newSol;
	}

	public int[] getAuxPipeBox() {
		return aux;
	}

    public void copyPipeBox(PipeBox pipebox) {
        aux = new int[pipebox.length()];
        for(int i=0; i < aux.length; i++) {
            aux[i] = pipebox.getSize(i);
        }
    }

	@Override
	public Iterator<Pipe> iterator() {
		return solution.descendingIterator();
	}
}
