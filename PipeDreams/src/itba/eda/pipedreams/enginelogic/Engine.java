package itba.eda.pipedreams.enginelogic;

import java.util.Deque;
import java.util.LinkedList;

import itba.eda.pipedreams.pipelogic.Pipe;
import itba.eda.pipedreams.pipelogic.PipeBox;
import itba.eda.pipedreams.pipelogic.PipeFactory;
import itba.eda.pipedreams.tablelogic.Board;
import itba.eda.pipedreams.tablelogic.Dir;
import itba.eda.pipedreams.tablelogic.Tile;

public class Engine {
	
	public static int X_PIPE_ID = 6;
	
	private Algorithm used_algorithm;
	
	private PipeBox pipeBox;
	private Board board;
	
	public Engine(Algorithm alg, int sizeX, int sizeY, int startX, int startY, String dir) {
		
		pipeBox = new PipeBox();
		
		board = Board.getInstance();
		board.loadBoard(sizeX, sizeY);
		
		board.setFlow(startX, startY, Dir.getBySymbol(dir));
	
		used_algorithm = alg;
	}
	
	public void start() {
		
		//TODO: Notify frontend observers
		Deque<Pipe> longestPath = new LinkedList<Pipe>();
		Deque<Pipe> currPath = new LinkedList<Pipe>();
		
		Tile origin = board.getTile(board.getXFlow(), board.getYFlow());
		
		switch (used_algorithm){
			
			case RecursiveBacktracking:
				RecursiveBacktracking(origin.getNext(board.getDirFlow()), board.getDirFlow(), currPath, longestPath);
				break;
		}
	}
	
	public void RecursiveBacktracking(Tile destiny_tile, Dir destiny_dir, Deque<Pipe> current, Deque<Pipe> longest){
		
		Pipe new_pipe;
		Dir new_destiny;
		
		//Solution
		if (destiny_tile == null){
			if(current.size() > longest.size())
				System.out.println("TODO: Copy new solution");
			return;
		}
		
		//Blocked
		if (destiny_tile.isBlocked())
			return;
		
		//Optimization: No more pipes
		
		//There's a pipe
		if (destiny_tile.hasPipe()){
			if (destiny_tile.getPipe().equals(PipeFactory.getPipe(X_PIPE_ID))){
				current.push(destiny_tile.getPipe());
				RecursiveBacktracking(destiny_tile.getNext(destiny_dir), destiny_dir, current, longest);
				current.pop();
			}
			return;
		}
		
		for (int i = 0; i < pipeBox.getSize(); i++){
			
			new_pipe = pipeBox.getItem(i);
			new_destiny = new_pipe.flow(destiny_dir);
			
			if (pipeBox.hasItem(i) && new_pipe.canFlow(destiny_dir)){
				
				pipeBox.remove(i);
				destiny_tile.setPipe(new_pipe);
				current.push(new_pipe);
				
				RecursiveBacktracking(destiny_tile.getNext(new_destiny), new_destiny, current, longest);
				
				current.pop();
				destiny_tile.removePipe();
				pipeBox.add(i);
			}
		}
		
		
		
	}
	
}
