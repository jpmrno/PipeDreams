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
	
	public Point getNext(Dir dir){
		switch (dir){
			case NORTH:
				return new Point(x - 1, y);
			case SOUTH:
				return new Point(x + 1, y);
			case EAST:
				return new Point(x, y + 1);
			case WEST:
				return new Point(x, y - 1);
			default:
				return null;
		}
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
	
	public Pipe getPipe() {
		return pipe;
	}
}
