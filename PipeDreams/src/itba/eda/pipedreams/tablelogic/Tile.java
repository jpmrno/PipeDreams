package itba.eda.pipedreams.tablelogic;
import itba.eda.pipedreams.pipelogic.Pipe;

public class Tile {
	
	private Pipe pipe;
	private boolean blocked;
	
	int x;
	int y;
	
	public Tile(int x, int y, boolean blocked){
		this.pipe = null;
		this.blocked = blocked;
		this.x = x;
		this.y = y;
	}
	
	public Tile getNext(Dir dir){
		switch (dir){
		
			case NORTH:
				return Board.getInstance().getTile(x - 1, y);
			case SOUTH:
				return Board.getInstance().getTile(x + 1, y);
			case EAST:
				return Board.getInstance().getTile(x, y + 1);
			case WEST:
				return Board.getInstance().getTile(x, y - 1);
			default:
				return null;
		}
	}
	
	public boolean isLegal(){
		return (pipe == null && blocked == false);
	}
	
	public boolean isBlocked(){
		return blocked;
	}
	
	public boolean hasPipe(){
		return (pipe != null);
	}
	
	public void setPipe(Pipe pipe){
		this.pipe = pipe;
	}
	
	public void removePipe(){
		this.pipe = null;
	}
	
	public int getPosX() {
		return x;
	}
	
	public int getPosY() {
		return y;
	}
	
	public Pipe getPipe() {
		return pipe;
	}
}
