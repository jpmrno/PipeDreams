package itba.eda.pipedreams.pipelogic;

public class PipeFactory {
	private Pipe[] pipes = new Pipe[ new Pipe() { //W <-> N

	},new Pipe() {	//N <-> E
		boolean canFlow(Dir from) {
			return from == Dir.NORTH || from == Dir.EAST;
		}

		Dir flow(Dir from) {
			return from == Dir.NORTH ? Dir.EAST : Dir.NORTH;
		}
	},new Pipe() {	//S <-> E
		boolean canFlow(Dir from) {
			return from == Dir.SOUTH || from == Dir.EAST;
		}

		Dir flow(Dir from) {
			return from == Dir.SOUTH ? Dir.EAST : Dir.SOUTH;
		}
	},new Pipe() {	//S <-> W
		boolean canFlow(Dir from) {
			return from == Dir.SOUTH || from == Dir.WEST;
		}

		Dir flow(Dir from) {
			return from == Dir.SOUTH ? Dir.WEST : Dir.SOUTH;
		}
	},new Pipe() {	//S <-> N
		boolean canFlow(Dir from) {
			return from == Dir.SOUTH || from == Dir.NORTH;
		}

		Dir flow(Dir from) {
			return from == Dir.SOUTH ? Dir.NORTH : Dir.SOUTH;
		}
	},new Pipe() {	//W <-> N
		boolean canFlow(Dir from) {
			return from == Dir.WEST || from == Dir.NORTH;
		}

		Dir flow(Dir from) {
			return from == Dir.WEST ? Dir.NORTH : Dir.WEST;
		}
	},new Pipe() {	//S <-> N, W <-> E
		boolean canFlow(Dir from) {
			return true;
		}

		Dir flow(Dir from) {
			if(from == Dir.NORTH)
				return Dir.SOUTH;
			else if(from == Dir.SOUTH)
				return Dir.NORTH;
			else if(from == Dir.WEST)
				return Dir.EAST;
			return DIR.WEST;
		}
	} ];

	public Pipe getPipe(int pipeType) {
		return pipes[pipeType];
	}
}