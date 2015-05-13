package itba.eda.pipedreams.enginelogic;

import java.util.Deque;
import java.util.LinkedList;

import itba.eda.pipedreams.pipelogic.Pipe;
import itba.eda.pipedreams.pipelogic.PipeBox;
import itba.eda.pipedreams.tablelogic.Board;
import itba.eda.pipedreams.tablelogic.Dir;
import itba.eda.pipedreams.tablelogic.Point;
import itba.eda.pipedreams.tablelogic.Tile;

public class Engine {
	private PipeBox pipeBox;
	private Board board;
	private Dir initialFlow;
	
	public Engine(int sizeX, int sizeY, int startX, int startY, String dir) {
		pipeBox = new PipeBox();
		board = new Board(sizeX, sizeY);
		initialFlow = Dir.getBySymbol(dir);
	}
	
	public void start() {
		Deque<Tile> longestPath = new LinkedList<Tile>();
		Deque<Tile> currPath = new LinkedList<Tile>();
		runRec(currPath, longestPath, Dir.invert(initialFlow));
	}

	//TODO probablemente sea mejor ver la posicion a la cual voy a ir para no tener que devolver booleanos porque como diferenciariamos el caso en el que estoy volviendo por un camino y cuando volvi del borde de la matriz
	private boolean runRec(Deque<Tile> currPath, Deque<Tile> longestPath, Dir from) {
		Tile last = currPath.getLast();
		Point current = last.getNext(from);
		
		if(!board.withinLimits(current)) {
			return true;
		} else if(last.isBlocked()) {
			if(!last.hasPipe())
				return false;
			else if(last.getPipe() == pipeBox.getItem(6)); //TODO Comparacion del pipe cruz
		}
		
		for(int i=0; i < pipeBox.getSize(); i++) {
			if(pipeBox.hasItem(i)) {
				Pipe pipe = pipeBox.getItem(i);
				if(pipe.canFlow(from)) {
					
					pipeBox.remove(i);
					runRec(currPath, longestPath, pipeBox.getItem(i).flow(from)); //TODO: Se puede abstraer mas
					pipeBox.add(i);
				}
			}
		}
	}
	
//	private static class TileNode {
//		private int pipeType;
//		private int posX, posY;
//		
//		public TileNode(int pipeType, int posX, int posY) {
//			this.pipeType = pipeType;
//			this.posX = posX;
//			this.posY = posY;
//		}
//	}
}
