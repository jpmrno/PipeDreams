package itba.eda.pipedreams.enginelogic;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import itba.eda.pipedreams.pipelogic.Pipe;
import itba.eda.pipedreams.pipelogic.PipeBox;
import itba.eda.pipedreams.tablelogic.Board;
import itba.eda.pipedreams.tablelogic.Dir;
import itba.eda.pipedreams.tablelogic.Point;
import itba.eda.pipedreams.tablelogic.Tile;

public class Engine {
	
	private Algorithm usedAlgorithm;
	
	private PipeBox pipeBox;
	private Board board;
	Timer timer = new Timer();
	
	public Engine(Algorithm alg, Board board, PipeBox pipeBox) {
		this.pipeBox = pipeBox;
		this.board = board;
		usedAlgorithm = alg;
	}
	
	public void start() {
		//TODO: Notify frontend observers
		Deque<Pipe> longestPath = new LinkedList<Pipe>();
		Deque<Pipe> currPath = new LinkedList<Pipe>();
		Tile origin = board.getOriginTile();
		
		timer.startClock();

		switch (usedAlgorithm){
			case RecursiveBacktracking:
				RecursiveBacktracking(board.getTile(origin.getNext(board.getDirFlow())), board.getDirFlow(), currPath, longestPath);
				break;
			default:
				break;
		}

		timer.stopClock();
		timer.printRunningTime();

		System.out.println("Max: " + longestPath.size());
		board.print();
	}

	/**
	 *
	 * @param curr Tile actual
	 * @param from desde que punto cardinal vengo
	 * @param currentPath
	 * @param longestPath
	 */
	public void RecursiveBacktracking(Tile curr, Dir from, Deque<Pipe> currentPath, Deque<Pipe> longestPath){
		//TODO: REMOVE DEBUG PRINT
//		board.print();

		if (curr == null){
//			System.out.println("Solution found");
			if(currentPath.size() > longestPath.size()){
				copyFromScratch(currentPath, longestPath);
			}
			return;
		}

		if (curr.isBlocked()){
//			System.out.println("Blocked");
			return;
		}

		if (curr.hasPipe()){
//			System.out.println("Current Tile already has a pipe");
			if (curr.getPipe().getId() == PipeBox.CROSS_PIPE_ID){
				Dir to = pipeBox.getItem(PipeBox.CROSS_PIPE_ID).flow(from);
				currentPath.push(curr.getPipe());
				RecursiveBacktracking(board.getTile(curr.getNext(to)), to.getOpposite(), currentPath, longestPath);
				currentPath.pop();
			}
			return;
		}

		if(pipeBox.isEmpty()){
//			System.out.println("PipeBox is empty");
			return;
		}
		
		for (int i = 0; i < pipeBox.getPipeSize(); i++){
//			System.out.println("Consultando por pipe: " + pipeBox.getItem(i).getId() + " Cantidad de elementos: " + pipeBox.getPipeTypeSize(i));

			Pipe newPipe = pipeBox.getItem(i);
			Dir to = newPipe.flow(from);
			
			if (pipeBox.hasItem(i) && newPipe.canFlow(from)){
				
				pipeBox.remove(i);
				curr.setPipe(newPipe);
				currentPath.push(newPipe);

				Point next = curr.getNext(to); //Consigo la proxima posicion a la que tengo que ir
				RecursiveBacktracking(board.getTile(next), to.getOpposite(), currentPath, longestPath);
				
				currentPath.pop();
				curr.removePipe();
				pipeBox.add(i);
			}
		}
		
	}
	
	public static void copyFromScratch(Queue<Pipe> from, Queue<Pipe> to) {
		//TODO: Se puede optimizar recorriendo los 2 y removiendo hasta que encuentre uno que sean iguales(en posicion o en tipo de pipe?)
		while(!to.isEmpty())
			to.remove();
		for (Pipe aux: from){
			to.add(aux);
		}
		
	}
	
}
