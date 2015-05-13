package itba.eda.pipedreams.tablelogic;

public class Tile {
	
	//private Pipe pipe;
	private boolean blocked;
	
	int x;
	int y;
	
	public Tile(int x, int y){
		//this.pipe = null;
		this.blocked = false;
		
		this.x = x;
		this.y = y;
	}
	
	public boolean isEmpty(){
		return (/*pipe == null &&*/ blocked == false);
	}
	
	public boolean isBlocked(){
		return blocked;
	}
	
}
