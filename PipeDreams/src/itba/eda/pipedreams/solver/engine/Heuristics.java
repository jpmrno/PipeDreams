package itba.eda.pipedreams.solver.engine;

import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.board.Board;
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
        public int apply(BasicBoard board, Point p, GameSolution sol, PipeBox pipeBox, Dir from) {
            if (board.isEmpty(p.goS()) && board.isEmpty(p.goE()) && board.isEmpty(p.goSE())) { //EL MEJOR CASO
                if (pipeBox.hasPipe(Pipe.CROSS) && pipeBox.hasPipe(Pipe.L4) && pipeBox.hasPipe(Pipe.L2)) {
                    Iterator<Pipe> it = from == Dir.WEST ? replace.iterator() : replace.descendingIterator();
                    while(it.hasNext()) {
                        sol.add(it.next());
                    }
                    return 1;
                }
            }
            return 0;
        }
    },
    L2 {
        private Deque<Pipe> replace = new LinkedList<>(Arrays.asList(Pipe.CROSS, Pipe.L3, Pipe.L2, Pipe.L1, Pipe.CROSS));
        @Override
        public int apply(BasicBoard board, Point p, GameSolution sol, PipeBox pipeBox, Dir from) {
            if (board.isEmpty(p.goS()) && board.isEmpty(p.goW()) && board.isEmpty(p.goSW())) { //EL MEJOR CASO
                if (pipeBox.hasPipe(Pipe.CROSS) && pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L3)) {
                    Iterator<Pipe> it = from == Dir.EAST ? replace.iterator() : replace.descendingIterator();
                    while(it.hasNext()) {
                        sol.add(it.next());
                    }
                    return 1;
                }
            } else if(from == Dir.EAST) {
                Point next = p.goN();
                if(board.withinLimits(next)) {
                    if(board.getPipe(next) == Pipe.I1) {
                        if (pipeBox.hasPipe(Pipe.I2) && pipeBox.hasPipe(Pipe.L3) && pipeBox.hasPipe(Pipe.L1)) {
                            if (board.isEmpty(p.goW()) && board.isEmpty(p.goNW())) {
                                sol.add(Pipe.I2);
                                sol.add(Pipe.L2);
                                sol.add(Pipe.L3);
                                sol.add(Pipe.L1);

                                return 2;
                            }
                        }
                    }
                }
            } else { //from == Dir.NORTH

            }
            return 0;
        }
    },
    L3 {
        private Deque<Pipe> replace = new LinkedList<>(Arrays.asList(Pipe.CROSS, Pipe.L2, Pipe.L3, Pipe.L4, Pipe.CROSS));
        @Override
        public int apply(BasicBoard board, Point p, GameSolution sol, PipeBox pipeBox, Dir from) {
            if (board.isEmpty(p.goW()) && board.isEmpty(p.goNW()) && board.isEmpty(p.goN())) { //EL MEJOR CASO
                if (pipeBox.hasPipe(Pipe.CROSS) && pipeBox.hasPipe(Pipe.L4) && pipeBox.hasPipe(Pipe.L2)) {
                    Iterator<Pipe> it = from == Dir.EAST ? replace.iterator() : replace.descendingIterator();
                    while (it.hasNext()) {
                        sol.add(it.next());
                    }
                    return 1;
                }
            } else if(from == Dir.EAST) {

            } else { //from == Dir.SOUTH

            }
            return 0;
        }
    },
    L4 {
        private Deque<Pipe> replace = new LinkedList<>(Arrays.asList(Pipe.CROSS, Pipe.L1, Pipe.L4, Pipe.L3, Pipe.CROSS));
        @Override
        public int apply(BasicBoard board, Point p, GameSolution sol, PipeBox pipeBox, Dir from) {
            if(board.isEmpty(p.goE()) && board.isEmpty(p.goNE()) && board.isEmpty(p.goN())) { //EL MEJOR CASO
                if(pipeBox.hasPipe(Pipe.CROSS) && pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L3)) {
                    Iterator<Pipe> it = from == Dir.WEST ? replace.iterator() : replace.descendingIterator();
                    while (it.hasNext()) {
                        sol.add(it.next());
                    }
                    return 1;
                }
            } else if(from == Dir.WEST) {
                Point next = p.goS();
                if(board.withinLimits(next)) {
                    if(board.getPipe(p.goS()) == Pipe.I1) {
                        if ((pipeBox.hasPipe(Pipe.I2) || pipeBox.hasPipe(Pipe.CROSS)) && pipeBox.hasPipe(Pipe.L4) && pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L3)) {
                            if (board.isEmpty(p.goE()) && board.isEmpty(p.goSE())) {
                                sol.add(pipeBox.hasPipe(Pipe.I2) ? Pipe.I2 : Pipe.CROSS);
                                sol.add(Pipe.L4);
                                sol.add(Pipe.L1);
                                sol.add(Pipe.L3);

                                return 2;
                            }
                        }
                    }
                }
            } else { //from == Dir.SOUTH

            }
            return 0;
        }
    },
    I1{
        private Deque<Pipe> replace = new LinkedList<>(Arrays.asList(Pipe.L3, Pipe.L1, Pipe.L4, Pipe.L2));
        private Deque<Pipe> replace2 = new LinkedList<>(Arrays.asList(Pipe.L4, Pipe.L2, Pipe.L3, Pipe.L1));
        @Override
        public int apply(BasicBoard board, Point p, GameSolution sol, PipeBox pipeBox, Dir from) {
            Point next = BasicBoard.getNext(p, from.opposite());

            // I1 seguido de I1
            if (board.withinLimits(next)) {
                if (board.getPipe(next) == Pipe.I1) {
                    boolean hasPipes = pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L2) && pipeBox.hasPipe(Pipe.L3) && pipeBox.hasPipe(Pipe.L4);
                    if (hasPipes) {
                        if (from == Dir.NORTH) {
                            if (board.isEmpty(p.goE()) && board.isEmpty(p.goSE())) {
                                Iterator<Pipe> it = replace.descendingIterator();
                                while (it.hasNext())
                                    sol.add(it.next());

                                return 2;
                            } else if (board.isEmpty(p.goW()) && board.isEmpty(p.goSW())) {
                                Iterator<Pipe> it = replace2.descendingIterator();
                                while (it.hasNext())
                                    sol.add(it.next());

                                return 2;
                            }
                        } else { //from == Dir.SOUTH
                            if (board.isEmpty(p.goE()) && board.isEmpty(p.goNE())) {
                                Iterator<Pipe> it = replace.iterator();
                                while (it.hasNext())
                                    sol.add(it.next());

                                return 2;
                            } else if (board.isEmpty(p.goW()) && board.isEmpty(p.goNW())) {
                                Iterator<Pipe> it = replace2.iterator();
                                while (it.hasNext())
                                    sol.add(it.next());

                                return 2;
                            }
                        }
                    }
                }
            }

            //I1 seguido de algun L
            Point nextPoint = BasicBoard.getNext(p, from.opposite()); //TODO: No deberia necesitarse volver a tomar el next

            //Vengo desde SOUTH
            if(board.withinLimits(nextPoint)) {
                if (board.getPipe(next) == Pipe.L4 && board.isEmpty(p.goE()) && board.isEmpty(p.goNE()) && pipeBox.hasPipe(Pipe.L3) && pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.I2)) {
                    sol.add(Pipe.L3);
                    sol.add(Pipe.L1);
                    sol.add(Pipe.L4);
                    sol.add(Pipe.I2);
                    return 2;
                }
                if (board.getPipe(next) == Pipe.L3 && board.isEmpty(p.goW()) && board.isEmpty(p.goNW()) && pipeBox.hasPipe(Pipe.L4) && pipeBox.hasPipe(Pipe.L2) && pipeBox.hasPipe(Pipe.I2)) {
                    sol.add(Pipe.L4);
                    sol.add(Pipe.L2);
                    sol.add(Pipe.L3);
                    sol.add(Pipe.I2);
                    return 2;
                }

                //Vengo desde NORTH
                if (board.getPipe(next) == Pipe.L1 && board.isEmpty(p.goE()) && board.isEmpty(p.goSE()) && pipeBox.hasPipe(Pipe.L2) && pipeBox.hasPipe(Pipe.L4) && pipeBox.hasPipe(Pipe.I2)) {
                    sol.add(Pipe.L2);
                    sol.add(Pipe.L4);
                    sol.add(Pipe.L1);
                    sol.add(Pipe.I2);
                    return 2;
                }
                if (board.getPipe(next) == Pipe.L2 && board.isEmpty(p.goW()) && board.isEmpty(p.goSW()) && pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L3) && pipeBox.hasPipe(Pipe.I2)) {
                    sol.add(Pipe.L1);
                    sol.add(Pipe.L3);
                    sol.add(Pipe.L2);
                    sol.add(Pipe.I2);
                    return 2;
                }
            }
            return 0;
        }
    },
    I2 {
        private Deque<Pipe> replace = new LinkedList<>(Arrays.asList(Pipe.L1, Pipe.L3, Pipe.L4, Pipe.L2));
        private Deque<Pipe> replace2 = new LinkedList<>(Arrays.asList(Pipe.L4, Pipe.L2, Pipe.L1, Pipe.L3));
        @Override
        public int apply(BasicBoard board, Point p, GameSolution sol, PipeBox pipeBox, Dir from) {
            Point next = BasicBoard.getNext(p, from.opposite());
            if (board.withinLimits(next)) {
                if (board.getPipe(next) == Pipe.I2) { //Caso generico para 2 "--"
                    boolean hasPipes = pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L2) && pipeBox.hasPipe(Pipe.L3) && pipeBox.hasPipe(Pipe.L4);
                    if (hasPipes) {
                        Iterator<Pipe> it = null;
                        if (from == Dir.WEST) { //TODO: DE ESTA MANERA PUEDE PISAR LA SOL ANTERIOR, NO SE POR QUE
                            if (board.isEmpty(p.goN()) && board.isEmpty(p.goNE())) {
                                it = replace.iterator(); //L1 L3 L4 L2
                                while (it.hasNext()) {
                                    Pipe curr = it.next();
                                    System.out.print(curr);
                                    sol.add(curr);
                                    return 2;
                                }
                            } else if (board.isEmpty(p.goS()) && board.isEmpty(p.goSE())) {
                                it = replace2.iterator(); //L4 L2 L1 L3
                                while (it.hasNext()) {
                                    Pipe curr = it.next();
                                    System.out.print(curr);
                                    sol.add(curr);
                                    return 2;
                                }
                            }
                        } else {
                            if (board.isEmpty(p.goN()) && board.isEmpty(p.goNW())) {
                                it = replace.descendingIterator(); //L2 L4 L3 L1
                                while (it.hasNext()) {
                                    Pipe curr = it.next();
                                    System.out.print(curr);
                                    sol.add(curr);
                                    return 2;
                                }
                            } else if (board.isEmpty(p.goS()) && board.isEmpty(p.goSW())) {
                                it = replace2.descendingIterator(); //L3 L1 L2 L4
                                Pipe curr = it.next();
                                System.out.print(curr);
                                sol.add(curr);
                                return 2;
                            }
                            System.out.println();

                        }
                        return 0;
                    }
                } else if(from == Dir.WEST) {
                    if (board.getPipe(next) == Pipe.L1 && board.isEmpty(p.goS()) && board.isEmpty(p.goSE()) && pipeBox.hasPipe(Pipe.L4) && pipeBox.hasPipe(Pipe.L2) && pipeBox.hasPipe(Pipe.I1)) {
                        sol.add(Pipe.L4);
                        sol.add(Pipe.L2);
                        sol.add(Pipe.L1);
                        sol.add(Pipe.I1);
                        return 2;
                    } else if (board.getPipe(next) == Pipe.L4 && board.isEmpty(p.goN()) && board.isEmpty(p.goNE()) && pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.L3) && pipeBox.hasPipe(Pipe.I1)) {
                        sol.add(Pipe.L1);
                        sol.add(Pipe.L3);
                        sol.add(Pipe.L4);
                        sol.add(Pipe.I1);
                        return 2;
                    }
                } else {
                    if (board.getPipe(next) == Pipe.L1 && board.isEmpty(p.goS()) && board.isEmpty(p.goSW()) && pipeBox.hasPipe(Pipe.L3) && pipeBox.hasPipe(Pipe.L1) && pipeBox.hasPipe(Pipe.I1)) {
                        sol.add(Pipe.L3);
                        sol.add(Pipe.L1);
                        sol.add(Pipe.L2);
                        sol.add(Pipe.I1);
                        return 2;
                    } else if (board.getPipe(next) == Pipe.L1 && board.isEmpty(p.goN()) && board.isEmpty(p.goNW()) && pipeBox.hasPipe(Pipe.L2) && pipeBox.hasPipe(Pipe.L4) && pipeBox.hasPipe(Pipe.I1)) {
                        sol.add(Pipe.L2);
                        sol.add(Pipe.L4);
                        sol.add(Pipe.L3);
                        sol.add(Pipe.I1);
                        return 2;
                    }
                }
            } else {
                //TODO: Estirarme por los bordes del tablero
            }
            return 0;
        }
    },
    CROSS {
        @Override
        public int apply(BasicBoard board, Point p, GameSolution sol, PipeBox pipeBox, Dir from) {
            return 0;
        }
    };

	public static int apply(BasicBoard board, Point point, Dir flow, PipeBox pipeBox, GameSolution solution) {
		return values()[board.getPipe(point).ordinal()].apply(board, point, solution, pipeBox, flow);
	}
}