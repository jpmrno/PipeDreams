package itba.eda.pipedreams.tablelogic;
import itba.eda.pipedreams.pipelogic.Pipe;

public class Tile {
	
	private Pipe pipe;
	private boolean blocked;
	
	Point pos;
	
	public Tile(int x, int y){
		//this.pipe = null;
		this.blocked = false;
		pos = new Point(x,y);
	}
	
	public boolean isLegal(){
		return (/*pipe == null &&*/ blocked == false);
	}
	
	public boolean isBlocked(){
		return blocked;
	}
	
	public boolean hasPipe(){
		return (pipe != null);
	}
	
	public int getPosX() {
		return pos.getX();
	}
	
	public int getPosY() {
		return pos.getY();
	}
	
	public Point getNext(Dir to) {
		switch (to){
		case NORTH:
			return new Point(pos.getX()+1, pos.getY());		
		case SOUTH:
			return new Point(pos.getX()-1, pos.getY());
		case WEST:
			return new Point(pos.getX(), pos.getY()+1);
		case EAST:
			return new Point(pos.getX(), pos.getY()-1);
		default:
			return null;
		}
	}

	public Pipe getPipe() {
		return pipe;
	}
}
