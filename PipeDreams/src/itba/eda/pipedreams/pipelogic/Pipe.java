package itba.eda.pipedreams.pipelogic;

import itba.eda.pipedreams.tablelogic.Dir;

public enum Pipe implements BasicPipe { // TODO: Remove canFlow()?
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

	// TODO: Which implementation?
//	L1("WN"),
//	L2("EN"),
//	L3("ES"),
//	L4("WS"),
//	I1("SN"),
//	I2("WE"),
//	CROSS("SNWE");
//
//	private PairDir[] directions;
//
//	Pipe(String dirs) {
//		if(dirs == null || dirs.length() % 2 != 0) {
//			throw new IllegalArgumentException();
//		}
//
//		this.directions = new PairDir[dirs.length() / PairDir.SIZE];
//
//		for(int i = 0; i < directions.length; i++) {
//			Dir first = Dir.getBySymbol(dirs.charAt(i * PairDir.SIZE));
//			Dir second = Dir.getBySymbol(dirs.charAt(i * PairDir.SIZE + 1));
//			directions[i] = new PairDir(first , second);
//		}
//	}
//
//	@Override
//	public boolean canFlow(Dir from) {
//		for(PairDir dirs : directions) {
//			if(dirs.contains(from)) {
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	@Override
//	public Dir flow(Dir from) {
//		for(PairDir dirs : directions) {
//			Dir to = dirs.getTo(from);
//			if(to != null) {
//				return to;
//			}
//		}
//		return null;
//	}
}
