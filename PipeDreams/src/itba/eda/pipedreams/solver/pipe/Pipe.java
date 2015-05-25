package itba.eda.pipedreams.solver.pipe;

import itba.eda.pipedreams.solver.board.Dir;

public enum Pipe implements BasicPipe { // TODO: Remove canFlow()? && TODO: inside PipeBox?
	L1 {
		@Override
		public boolean canFlow(Dir from) {
			return from == Dir.NORTH || from == Dir.WEST;
		}

		@Override
		public Dir flow(Dir from) {
			if(from == Dir.NORTH)
				return Dir.WEST;
			if(from == Dir.WEST)
				return Dir.NORTH;
			return null;
		}
	},
	L2 {
		@Override
		public boolean canFlow(Dir from) {
			return from == Dir.NORTH || from == Dir.EAST;
		}

		@Override
		public Dir flow(Dir from) {
			if(from == Dir.NORTH)
				return Dir.EAST;
			if(from == Dir.EAST)
				return Dir.NORTH;
			return null;
		}
	},
	L3 {
		@Override
		public boolean canFlow(Dir from) {
			return from == Dir.SOUTH || from == Dir.EAST;
		}

		@Override
		public Dir flow(Dir from) {
			if(from == Dir.SOUTH)
				return Dir.EAST;
			if(from == Dir.EAST)
				return Dir.SOUTH;
			return null;
		}
	},
	L4 {
		@Override
		public boolean canFlow(Dir from) {
			return from == Dir.SOUTH || from == Dir.WEST;
		}

		@Override
		public Dir flow(Dir from) {
			if(from == Dir.SOUTH)
				return Dir.WEST;
			if(from == Dir.WEST)
				return Dir.SOUTH;
			return null;
		}
	},
	I1 {
		@Override
		public boolean canFlow(Dir from) {
			return from == Dir.SOUTH || from == Dir.NORTH;
		}

		@Override
		public Dir flow(Dir from) {
			if(from == Dir.SOUTH)
				return Dir.NORTH;
			if(from == Dir.NORTH)
				return Dir.SOUTH;
			return null;
		}
	},
	I2 {
		@Override
		public boolean canFlow(Dir from) {
			return from == Dir.EAST || from == Dir.WEST;
		}

		@Override
		public Dir flow(Dir from) {
			if(from == Dir.EAST)
				return Dir.WEST;
			if(from == Dir.WEST)
				return Dir.EAST;
			return null;
		}
	},
	CROSS {
		@Override
		public boolean canFlow(Dir from) {
			return from == Dir.NORTH || from == Dir.SOUTH || from == Dir.EAST || from == Dir.WEST;
		}

		@Override
		public Dir flow(Dir from) {
			if(from == Dir.EAST)
				return Dir.WEST;
			if(from == Dir.WEST)
				return Dir.EAST;
			if(from == Dir.SOUTH)
				return Dir.NORTH;
			if(from == Dir.NORTH)
				return Dir.SOUTH;
			return null;
		}
	}
}
