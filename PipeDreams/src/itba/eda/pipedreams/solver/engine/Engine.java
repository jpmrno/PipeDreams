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
import sun.awt.image.MultiResolutionCachedImage;

import java.util.*;

import static itba.eda.pipedreams.solver.board.Board.*;

public class Engine implements Runnable {
	private static final int DELAY = 1000;

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

		if(!board.withinLimits(point)) {
			if(withProgress) { // TODO: OK here?
				board.notifyObservers();
				Platform.runLater(display::saveAsPng);
				Thread.sleep(DELAY);
			}
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

	private boolean findRandomSolution(Point point, Dir to, Solution sol){
		Dir from = to.opposite();

		System.out.println(board);

		if(!board.withinLimits(point)) {
			return true;
		}

		if(!board.isEmpty(point)) {
			if(!board.isBlocked(point, from)) {
				Pipe pipe = board.getPipe(point);
				sol.push(pipe);

				if(findRandomSolution(BasicBoard.getNext(point, pipe.flow(from)), pipe.flow(from), sol)) {
					return true;
				}
				BasicBoard.getPrevious(point, pipe.flow(from));

				sol.pop();
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

				sol.push(pipe);

				if(findRandomSolution(BasicBoard.getNext(point, pipe.flow(from)), pipe.flow(from), sol)) {
					return true;
				}
				BasicBoard.getPrevious(point, pipe.flow(from));

				sol.pop();
				board.removePipe(point);
				pipeBox.addOnePipe(i);
			}
		}
		return false;
	}



    private void hillClimbing() { // TODO: Add notifies!
        time = 1000*60;
        Solution currSol = new Solution(), bestSol;
        Timer t = new Timer();
        t.startClock();

        if(!findRandomSolution(BasicBoard.getNext(board.getStartPoint().clone(), board.getStartFlow()), board.getStartFlow(), currSol)) {
            return;
        }

        bestSol = currSol.cloneSol();
        while(t.getRunningTime() < time) {
            currSol = findBestNeighbor(currSol);

            if(currSol != null) {
                bestSol = currSol.cloneSol();
                bestSol.cloneSol().applySolution(board);
                System.out.println("DEBUG> AuxPipeBox: " + bestSol.getAuxPipeBox());
                pipeBox = new PipeBox(bestSol.getAuxPipeBox()); //TODO: Arreglar
            } else {
                System.out.println("MAXIMO LOCAL");
                findRandomSolution(BasicBoard.getNext(board.getStartPoint().clone(), board.getStartFlow()), board.getStartFlow(), currSol);
            }
        } //TODO: Aplicar la mejor solucion y retornar
    }

    private Solution findBestNeighbor(Solution currSol) {
        Solution bestSol = null;
        Point currPoint = board.getStartPoint();
        Pipe  currPipe;
        Dir currFlow = board.getStartFlow();
        currPoint = BasicBoard.getNext(currPoint, currFlow); //TODO: Ver problema de metodos estaticos
        Iterator<Pipe> it = currSol.iterator();
        boolean noBetterSol = false;
        int counter = 0;

        while(it.hasNext() && !noBetterSol) {
            currPipe = it.next();
            Solution sol = getHeuristicSol(currPoint, currSol, counter);

            int bestSolLength = bestSol == null? 0 : bestSol.size();
            if(sol != null && sol.size() > bestSolLength) {
                bestSol = sol;

            }
            System.out.println(currPipe);
            //TODO: Cortar antes si se encuentra una solucion con pipe cruzado
            currPoint = BasicBoard.getNext(currPoint, currPipe.flow(currFlow.opposite()));
            currFlow = currPipe.flow(currFlow.opposite());
            counter++;
            for(Pipe pipe : bestSol) {
                System.out.print(pipe + " - ");
            }
            System.out.println();
        }
        return bestSol;
    }

    private Solution getHeuristicSol(Point p, Solution s, int counter) {
        Pipe pipe = board.getPipe(p);
        Iterator<Pipe> it = s.iterator();
        Solution sol = new Solution();

        sol.copyPipeBox(pipeBox);

        while (counter > 0) {
            sol.push(it.next());
            counter--;
        }

        switch(pipe) {
            case L1:
                if(pipeBox.getSize(Pipe.CROSS.ordinal()) > 0 && pipeBox.getSize(Pipe.L4.ordinal()) > 0 && pipeBox.getSize(Pipe.L2.ordinal()) > 0) {
                    if(board.isEmpty(new Point(p.getRow() + 1, p.getColumn())) && board.isEmpty(new Point(p.getRow(), p.getColumn() + 1))
                            && board.isEmpty(new Point(p.getRow() + 1, p.getColumn() + 1))) {
                        it.next();
                        sol.push(Pipe.CROSS);
                        sol.getAuxPipeBox()[Pipe.CROSS.ordinal()]--;
                        sol.push(Pipe.L4);
                        sol.getAuxPipeBox()[Pipe.L4.ordinal()]--;
                        sol.push(Pipe.L1);
                        sol.push(Pipe.L2);
                        sol.getAuxPipeBox()[Pipe.L2.ordinal()]--;
                        sol.push(Pipe.CROSS);

                    }
                    break;
                }
            default:
                break;

        }
        while(it.hasNext()) {
           sol.push(it.next());
        }
        return sol;
    }

}
