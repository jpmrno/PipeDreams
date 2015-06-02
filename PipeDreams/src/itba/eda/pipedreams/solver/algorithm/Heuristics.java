package itba.eda.pipedreams.solver.algorithm;

import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.board.Dir;
import itba.eda.pipedreams.solver.pipe.Pipe;
import itba.eda.pipedreams.solver.pipe.PipeBox;

import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public enum Heuristics implements Heuristic {
	L1 {
		private Deque<Pipe> replace = new LinkedList<>(Arrays.asList(Pipe.CROSS, Pipe.L4, Pipe.L1, Pipe.L2, Pipe.CROSS));

		@Override
		public int apply(BasicBoard board, Point p, Solution sol, PipeBox pipeBox, Dir from) {
			Point nextpoint = Point.getNext(p, Pipe.L1.flow(from).opposite());

			if(board.isEmpty(nextpoint) && board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
				if(pipeBox.hasPipe(Pipe.CROSS) && pipeBox.hasPipe(Pipe.L4) && pipeBox.hasPipe(Pipe.L2)) {
					Iterator<Pipe> it = from == Dir.WEST ? replace.iterator() : replace.descendingIterator();
					while(it.hasNext()) {
						sol.add(it.next());
					}
					return 1;
				}
			}

			nextpoint = Point.getNext(p, Pipe.L1.flow(from));

			if(board.withinLimits(nextpoint)) {
				Pipe nextpipe = board.getPipe(nextpoint);

				if(nextpipe == Pipe.L2) {
					if(pipeBox.hasPipe(Pipe.I1, 2)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I1);
							sol.add(Pipe.L1);
							sol.add(nextpipe);
							sol.add(Pipe.I1);
							return 2;
						}
					}
				}

				if(nextpipe == Pipe.L4) {
					if(pipeBox.hasPipe(Pipe.I2, 2)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I2);
							sol.add(Pipe.L1);
							sol.add(nextpipe);
							sol.add(Pipe.I2);
							return 2;
						}
					}
				}

				if(nextpipe == Pipe.I1) {
					if(pipeBox.hasPipe(Pipe.I2) && pipeBox.hasPipe(Pipe.L2) && pipeBox.hasPipe(Pipe.L4)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I2);
							sol.add(Pipe.L1);
							sol.add(Pipe.L4);
							sol.add(Pipe.L2);
							return 2;
						}
					}
				}

				if(nextpipe == Pipe.I2) {
					if(pipeBox.hasPipe(Pipe.I1) && pipeBox.hasPipe(Pipe.L2) && pipeBox.hasPipe(Pipe.L4)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I1);
							sol.add(Pipe.L1);
							sol.add(Pipe.L2);
							sol.add(Pipe.L4);
							return 2;
						}
					}
				}
			}
			return 0;
		}
	},
	L2 {
		private Deque<Pipe> replace = new LinkedList<>(Arrays.asList(Pipe.CROSS, Pipe.L3, Pipe.L2, Pipe.L1, Pipe.CROSS));

		@Override
		public int apply(BasicBoard board, Point p, Solution sol, PipeBox pipeBox, Dir from) {
			Point nextpoint = Point.getNext(p, Pipe.L2.flow(from).opposite());

			if(board.isEmpty(nextpoint) && board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
				if(pipeBox.hasPipe(Pipe.CROSS) && pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L3)) {
					Iterator<Pipe> it = from == Dir.EAST ? replace.iterator() : replace.descendingIterator();
					while(it.hasNext()) {
						sol.add(it.next());
					}
					return 1;
				}
			}

			nextpoint = Point.getNext(p, Pipe.L2.flow(from));

			if(board.withinLimits(nextpoint)) {
				Pipe nextpipe = board.getPipe(nextpoint);
				if(nextpipe == Pipe.L1) {
					if(pipeBox.hasPipe(Pipe.I1, 2)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I1);
							sol.add(Pipe.L2);
							sol.add(nextpipe);
							sol.add(Pipe.I1);
							return 2;
						}
					}
				}

				if(nextpipe == Pipe.L3) {
					if(pipeBox.hasPipe(Pipe.I2, 2)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I2);
							sol.add(Pipe.L2);
							sol.add(nextpipe);
							sol.add(Pipe.I2);
							return 2;
						}
					}
				}

				if(nextpipe == Pipe.I1) {
					if(pipeBox.hasPipe(Pipe.I2) && pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L3)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I2);
							sol.add(Pipe.L2);
							sol.add(Pipe.L3);
							sol.add(Pipe.L1);
							return 2;
						}
					}
				}

				if(nextpipe == Pipe.I2) {
					if(pipeBox.hasPipe(Pipe.I1) && pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L3)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I1);
							sol.add(Pipe.L2);
							sol.add(Pipe.L1);
							sol.add(Pipe.L3);
							return 2;
						}
					}
				}
			}
			return 0;
		}
	},
	L3 {
		private Deque<Pipe> replace = new LinkedList<>(Arrays.asList(Pipe.CROSS, Pipe.L2, Pipe.L3, Pipe.L4, Pipe.CROSS));

		@Override
		public int apply(BasicBoard board, Point p, Solution sol, PipeBox pipeBox, Dir from) {
			Point nextpoint = Point.getNext(p, Pipe.L3.flow(from).opposite());

			if(board.isEmpty(nextpoint) && board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
				if(pipeBox.hasPipe(Pipe.CROSS) && pipeBox.hasPipe(Pipe.L4) && pipeBox.hasPipe(Pipe.L2)) {
					Iterator<Pipe> it = from == Dir.EAST ? replace.iterator() : replace.descendingIterator();
					while(it.hasNext()) {
						sol.add(it.next());
					}

					return 1;
				}
			}

			nextpoint = Point.getNext(p, Pipe.L3.flow(from));

			if(board.withinLimits(nextpoint)) {
				Pipe nextpipe = board.getPipe(nextpoint);

				if(nextpipe == Pipe.L4) {
					if(pipeBox.hasPipe(Pipe.I1, 2)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I1);
							sol.add(Pipe.L3);
							sol.add(nextpipe);
							sol.add(Pipe.I1);
							return 2;
						}
					}
				}

				if(nextpipe == Pipe.L2) {
					if(pipeBox.hasPipe(Pipe.I2, 2)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I2);
							sol.add(Pipe.L3);
							sol.add(nextpipe);
							sol.add(Pipe.I2);
							return 2;
						}
					}
				}

				if(nextpipe == Pipe.I1) {
					if(pipeBox.hasPipe(Pipe.I2) && pipeBox.hasPipe(Pipe.L2) && pipeBox.hasPipe(Pipe.L4)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I2);
							sol.add(Pipe.L3);
							sol.add(Pipe.L2);
							sol.add(Pipe.L4);
							return 2;
						}
					}
				}

				if(nextpipe == Pipe.I2) {
					if(pipeBox.hasPipe(Pipe.I1) && pipeBox.hasPipe(Pipe.L2) && pipeBox.hasPipe(Pipe.L4)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I1);
							sol.add(Pipe.L3);
							sol.add(Pipe.L4);
							sol.add(Pipe.L2);
							return 2;
						}
					}
				}
			}

			return 0; // TODO: Ret -1?
		}
	},
	L4 {
		private Deque<Pipe> replace = new LinkedList<>(Arrays.asList(Pipe.CROSS, Pipe.L1, Pipe.L4, Pipe.L3, Pipe.CROSS));

		@Override
		public int apply(BasicBoard board, Point p, Solution sol, PipeBox pipeBox, Dir from) {
			Point nextpoint = Point.getNext(p, Pipe.L4.flow(from).opposite());

			if(board.isEmpty(nextpoint) && board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
				if(pipeBox.hasPipe(Pipe.CROSS) && pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L3)) {
					Iterator<Pipe> it = from == Dir.WEST ? replace.iterator() : replace.descendingIterator();
					while(it.hasNext()) {
						sol.add(it.next());
					}
					return 1;
				}
			}

			nextpoint = Point.getNext(p, Pipe.L4.flow(from));

			if(board.withinLimits(nextpoint)) {
				Pipe nextpipe = board.getPipe(nextpoint);
				if(nextpipe == Pipe.L3) {
					if(pipeBox.hasPipe(Pipe.I1, 2)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I1);
							sol.add(Pipe.L4);
							sol.add(nextpipe);
							sol.add(Pipe.I1);
							return 2;
						}
					}
				}

				if(nextpipe == Pipe.L1) {
					if(pipeBox.hasPipe(Pipe.I2, 2)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I2);
							sol.add(Pipe.L4);
							sol.add(nextpipe);
							sol.add(Pipe.I2);
							return 2;
						}
					}
				}

				if(nextpipe == Pipe.I1) {
					if(pipeBox.hasPipe(Pipe.I2) && pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L3)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I2);
							sol.add(Pipe.L4);
							sol.add(Pipe.L1);
							sol.add(Pipe.L3);
							return 2;
						}
					}
				}

				if(nextpipe == Pipe.I2) {
					if(pipeBox.hasPipe(Pipe.I1) && pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L3)) {
						if(board.isEmpty(Point.getNext(p, from.opposite())) && board.isEmpty(Point.getNext(nextpoint, from.opposite()))) {
							sol.add(Pipe.I1);
							sol.add(Pipe.L4);
							sol.add(Pipe.L3);
							sol.add(Pipe.L1);
							return 2;
						}
					}
				}
			}
			return 0;
		}
	},
	I1 {
		private Deque<Pipe> replace = new LinkedList<>(Arrays.asList(Pipe.L3, Pipe.L1, Pipe.L4, Pipe.L2));
		private Deque<Pipe> replace2 = new LinkedList<>(Arrays.asList(Pipe.L4, Pipe.L2, Pipe.L3, Pipe.L1));

		@Override
		public int apply(BasicBoard board, Point p, Solution sol, PipeBox pipeBox, Dir from) {
			Point nextpoint = Point.getNext(p, Pipe.I1.flow(from));
			if(board.withinLimits(nextpoint)) {
				Pipe nextPipe = board.getPipe(nextpoint);
				if(nextPipe == Pipe.I1) {
					if(pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L2) && pipeBox.hasPipe(Pipe.L3) && pipeBox.hasPipe(Pipe.L4)) {
						if(board.isEmpty(Point.getNext(p, Dir.EAST)) && board.isEmpty(Point.getNext(nextpoint, Dir.EAST))) {
							Iterator<Pipe> it = (from == Dir.NORTH) ? replace.descendingIterator() : replace.iterator();

							while(it.hasNext()) {
								sol.add(it.next());
							}

							return 2;
						} else if(board.isEmpty(Point.getNext(p, Dir.WEST)) && board.isEmpty(Point.getNext(nextpoint, Dir.WEST))) {
							Iterator<Pipe> it = (from == Dir.NORTH) ? replace2.descendingIterator() : replace2.iterator();

							while(it.hasNext()) {
								sol.add(it.next());
							}

							return 2;
						}
					}
				}

				if(nextPipe == Pipe.L1 || nextPipe == Pipe.L2 || nextPipe == Pipe.L3 || nextPipe == Pipe.L4) {
					Dir dir = nextPipe.flow(from).opposite();
					if(board.isEmpty(Point.getNext(p, dir)) && board.isEmpty(Point.getNext(nextpoint, dir))) {
						if(pipeBox.hasPipe(Pipe.I2)) {
							if(pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L3)) {
								if(nextPipe == Pipe.L2) {
									sol.add(Pipe.L1);
									sol.add(Pipe.L3);
								} else if(nextPipe == Pipe.L4) {
									sol.add(Pipe.L3);
									sol.add(Pipe.L1);
								}
							}

							if(pipeBox.hasPipe(Pipe.L2) && pipeBox.hasPipe(Pipe.L4)) {
								if(nextPipe == Pipe.L1) {
									sol.add(Pipe.L2);
									sol.add(Pipe.L4);
								} else if(nextPipe == Pipe.L3) {
									sol.add(Pipe.L4);
									sol.add(Pipe.L2);

								}
							}

							sol.add(nextPipe);
							sol.add(Pipe.I2);

							return 2;
						}
					}
				}
			}
			return 0;
		}
	},
	I2 {
		private Deque<Pipe> replace = new LinkedList<>(Arrays.asList(Pipe.L2, Pipe.L4, Pipe.L3, Pipe.L1));
		private Deque<Pipe> replace2 = new LinkedList<>(Arrays.asList(Pipe.L3, Pipe.L1, Pipe.L2, Pipe.L4));

		@Override
		public int apply(BasicBoard board, Point p, Solution sol, PipeBox pipeBox, Dir from) {
			Point nextpoint = Point.getNext(p, Pipe.I2.flow(from));
			if(board.withinLimits(nextpoint)) {
				Pipe nextPipe = board.getPipe(nextpoint);
				if(nextPipe == Pipe.I2) {
					if(pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L2) && pipeBox.hasPipe(Pipe.L3) && pipeBox.hasPipe(Pipe.L4)) {
						if(board.isEmpty(Point.getNext(p, Dir.NORTH)) && board.isEmpty(Point.getNext(nextpoint, Dir.NORTH))) {
							Iterator<Pipe> it = (from == Dir.WEST) ? replace.descendingIterator() : replace.iterator();

							while(it.hasNext()) {
								sol.add(it.next());
							}

							return 2;
						} else if(board.isEmpty(Point.getNext(p, Dir.SOUTH)) && board.isEmpty(Point.getNext(nextpoint, Dir.SOUTH))) {
							Iterator<Pipe> it = (from == Dir.WEST) ? replace2.descendingIterator() : replace2.iterator();

							while(it.hasNext()) {
								sol.add(it.next());
							}

							return 2;
						}
					}
				}

				if(nextPipe == Pipe.L1 || nextPipe == Pipe.L2 || nextPipe == Pipe.L3 || nextPipe == Pipe.L4) {
					Dir dir = nextPipe.flow(from).opposite();
					if(board.isEmpty(Point.getNext(p, dir)) && board.isEmpty(Point.getNext(nextpoint, dir))) {
						if(pipeBox.hasPipe(Pipe.I1)) {
							if(pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L3)) {
								if(nextPipe == Pipe.L2) {
									sol.add(Pipe.L3);
									sol.add(Pipe.L1);
								} else if(nextPipe == Pipe.L4) {
									sol.add(Pipe.L1);
									sol.add(Pipe.L3);
								}
							}

							if(pipeBox.hasPipe(Pipe.L2) && pipeBox.hasPipe(Pipe.L4)) {
								if(nextPipe == Pipe.L1) {
									sol.add(Pipe.L4);
									sol.add(Pipe.L2);
								} else if(nextPipe == Pipe.L3) {
									sol.add(Pipe.L2);
									sol.add(Pipe.L4);
								}
							}

							sol.add(nextPipe);
							sol.add(Pipe.I1);

							return 2;
						}
					}
				}
			}

			//TODO: Estirarme por los bordes del tablero

			return 0;
		}
	},
	CROSS {
		@Override
		public int apply(BasicBoard board, Point p, Solution sol, PipeBox pipeBox, Dir from) {
			return 0;
		}
	};

	public static int apply(BasicBoard board, Point point, Dir from, PipeBox pipeBox, Solution solution) {
		return values()[board.getPipe(point).ordinal()].apply(board, point, solution, pipeBox, from);
	}
}