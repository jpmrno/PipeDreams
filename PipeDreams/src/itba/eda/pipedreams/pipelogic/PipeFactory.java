package itba.eda.pipedreams.pipelogic;

import itba.eda.pipedreams.tablelogic.Dir;

public class PipeFactory {
	private static final Pipe[] pipes = { new Pipe() { //W <-> N
		public boolean canFlow(Dir from) {
			return from == Dir.WEST || from == Dir.NORTH;
		}

		public Dir flow(Dir from) {
			return from == Dir.WEST ? Dir.NORTH : Dir.WEST;
		}

		public int getId() {
			return 0;
		}
	},new Pipe() {	//N <-> E
		public boolean canFlow(Dir from) {
			return from == Dir.NORTH || from == Dir.EAST;
		}

		public Dir flow(Dir from) {
			return from == Dir.NORTH ? Dir.EAST : Dir.NORTH;
		}

		public int getId() {
			return 1;
		}
	},new Pipe() {	//S <-> E
		public boolean canFlow(Dir from) {
			return from == Dir.SOUTH || from == Dir.EAST;
		}

		public Dir flow(Dir from) {
			return from == Dir.SOUTH ? Dir.EAST : Dir.SOUTH;
		}

		public int getId() {
			return 2;
		}
	},new Pipe() {	//S <-> W
		public boolean canFlow(Dir from) {
			return from == Dir.SOUTH || from == Dir.WEST;
		}

		public Dir flow(Dir from) {
			return from == Dir.SOUTH ? Dir.WEST : Dir.SOUTH;
		}

		public int getId() {
			return 3;
		}
	},new Pipe() {	//S <-> N
		public boolean canFlow(Dir from) {
			return from == Dir.SOUTH || from == Dir.NORTH;
		}

		public Dir flow(Dir from) {
			return from == Dir.SOUTH ? Dir.NORTH : Dir.SOUTH;
		}

		public int getId() {
			return 4;
		}
	},new Pipe() {	//W <-> E
		public boolean canFlow(Dir from) {
			return from == Dir.WEST || from == Dir.EAST;
		}

		public Dir flow(Dir from) {
			return from == Dir.WEST ? Dir.EAST : Dir.WEST;
		}

		public int getId() {
			return 5;
		}
	},new Pipe() {	//S <-> N, W <-> E
		public boolean canFlow(Dir from) {
			return true;
		}

		public Dir flow(Dir from) {
			if(from == Dir.NORTH)
				return Dir.SOUTH;
			else if(from == Dir.SOUTH)
				return Dir.NORTH;
			else if(from == Dir.WEST)
				return Dir.EAST;
			return Dir.WEST;
		}

		public int getId() {
			return 6;
		}
	} };
	
	private PipeFactory() {
		
	}

	public static Pipe getPipe(int pipeType) {
		return pipes[pipeType];
	}
	
	public static int getPipeTypesSize() {
		return pipes.length;
	}
}