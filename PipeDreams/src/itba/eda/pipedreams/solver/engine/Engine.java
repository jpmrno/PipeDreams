package itba.eda.pipedreams.solver.engine;

import itba.eda.pipedreams.solver.basic.Method;
import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.board.Board;
import itba.eda.pipedreams.solver.board.Dir;
import itba.eda.pipedreams.solver.pipe.Pipe;
import itba.eda.pipedreams.solver.pipe.PipeBox;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Engine implements Runnable {
	private static final int DELAY = 1000;

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

					backtracking();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case APROX:
                try {
                    hillClimbing();
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



    private void hillClimbing() throws InterruptedException { // TODO: Add notifies!
        time = 1000*60;
        Solution currSol = new Solution(), bestSol;
        Timer t = new Timer();
        t.startClock();

        if(!findRandomSolution(BasicBoard.getNext(board.getStartPoint().clone(), board.getStartFlow()), board.getStartFlow(), currSol)) {
            return;
        }

        if(withProgress) { // TODO: OK here?
            board.notifyObservers();
            Thread.sleep(DELAY);
        }

        bestSol = currSol.cloneSol();
        while(t.getRunningTime() < time) {
            currSol = findBestNeighbor(currSol);

            if(currSol != null) {
                bestSol = currSol.cloneSol();
                bestSol.cloneSol().applySolution(board);
                if(withProgress) { // TODO: OK here?
                    board.notifyObservers();
                    Thread.sleep(DELAY);
                }
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
            Solution sol = getHeuristicSol(currPoint, currSol, counter, currFlow.opposite());

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

    private Solution getHeuristicSol(Point p, Solution s, int counter, Dir from) {
        Pipe pipe = board.getPipe(p);
        Iterator<Pipe> it = s.iterator();
        Solution sol = new Solution();

        sol.copyPipeBox(pipeBox);

        while (counter > 0) {
            sol.push(it.next());
            counter--;
        }

        int skip = Heuristics.getHeuristic(board, p, sol, pipeBox, from);
		while(skip != 0) {
			it.next();
			skip--;
		}

        while(it.hasNext()) {
           sol.push(it.next());
        }

        return sol;
    }

}
