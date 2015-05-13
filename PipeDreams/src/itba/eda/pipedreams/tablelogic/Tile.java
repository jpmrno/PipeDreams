package itba.eda.pipedreams.tablelogic;
import itba.eda.pipedreams.pipelogic.Pipe;

public class Tile {
	
	private Pipe pipe;
	private boolean blocked;
	
	int x;
	int y;
	
	public Tile(int x, int y){
		//this.pipe = null;
		this.blocked = false;
		
		this.x = x;
		this.y = y;
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
	
}
