package itba.eda.pipedreams.solver.engine;

import itba.eda.pipedreams.solver.basic.Point;
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
        public int apply(Board board, Point p, Solution sol, PipeBox pipeBox, Dir from) {

            if (board.isEmpty(p.goS()) && board.isEmpty(p.goE()) && board.isEmpty(p.goSE())) {
                if (sol.hasPipe(Pipe.CROSS) && sol.hasPipe(Pipe.L4) && sol.hasPipe(Pipe.L2)) {
                    Iterator<Pipe> it = from == Dir.WEST ? replace.iterator() : replace.descendingIterator();
                    Pipe curr;

                    while(it.hasNext()) {
                        curr = it.next();
                        sol.push(curr);
                        sol.removePipe(curr);
                    }

                    sol.addPipe(Pipe.L1);

                    return 1;
                }
            }
            return 0;
        }
    },
    L2 {

        private Deque<Pipe> replace = new LinkedList<>(Arrays.asList(Pipe.CROSS, Pipe.L3, Pipe.L2, Pipe.L1, Pipe.CROSS));
        @Override
        public int apply(Board board, Point p, Solution sol, PipeBox pipeBox, Dir from) {
            if (board.isEmpty(p.goS()) && board.isEmpty(p.goW()) && board.isEmpty(p.goSW())) {
                if (sol.hasPipe(Pipe.CROSS) && sol.hasPipe(Pipe.L1) && sol.hasPipe(Pipe.L3)) {
                    Iterator<Pipe> it = from == Dir.EAST ? replace.iterator() : replace.descendingIterator();
                    Pipe curr;
                    while(it.hasNext()) {
                        curr = it.next();
                        sol.push(curr);
                        sol.removePipe(curr);
                    }
                    sol.addPipe(Pipe.L2);

                    return 1;
                }
            } else if(board.getPipe(p.goN()) == Pipe.I1) {
                if(sol.hasPipe(Pipe.I2) && sol.hasPipe(Pipe.L3) && sol.hasPipe(Pipe.L1)) {
                    if(board.isEmpty(p.goW()) && board.isEmpty(p.goNW())) {
                        sol.addPipe(Pipe.I1);

                        sol.push(Pipe.I2);
                        sol.removePipe(Pipe.I2);
                        sol.push(Pipe.L2);
                        sol.push(Pipe.L3);
                        sol.removePipe(Pipe.L3);
                        sol.push(Pipe.L1);
                        sol.removePipe(Pipe.L3);

                        return 2;
                    }
                }
            }
            return 0;
        }
    },
    L3 {
        private Deque<Pipe> replace = new LinkedList<>(Arrays.asList(Pipe.CROSS, Pipe.L2, Pipe.L3, Pipe.L4, Pipe.CROSS));
        @Override
        public int apply(Board board, Point p, Solution sol, PipeBox pipeBox, Dir from) {
            if (board.isEmpty(p.goS()) && board.isEmpty(p.goE()) && board.isEmpty(p.goSE())) {
                if (sol.hasPipe(Pipe.CROSS) && sol.hasPipe(Pipe.L4) && sol.hasPipe(Pipe.L2)) {
                    Iterator<Pipe> it = from == Dir.EAST ? replace.iterator() : replace.descendingIterator();
                    Pipe curr;

                    while (it.hasNext()) {
                        curr = it.next();
                        sol.push(curr);
                        sol.removePipe(curr);
                    }

                    sol.addPipe(Pipe.L3);

                    return 1;
                }
            }
            return 0;
        }
    },
    L4 {
        private Deque<Pipe> replace = new LinkedList<>(Arrays.asList(Pipe.CROSS, Pipe.L1, Pipe.L4, Pipe.L3, Pipe.CROSS));
        @Override
        public int apply(Board board, Point p, Solution sol, PipeBox pipeBox, Dir from) {
            if(board.isEmpty(p.goE()) && board.isEmpty(p.goNE()) && board.isEmpty(p.goN())) {
                if(sol.hasPipe(Pipe.CROSS) && sol.hasPipe(Pipe.L1) && sol.hasPipe(Pipe.L3)) {
                    Iterator<Pipe> it = from == Dir.EAST ? replace.iterator() : replace.descendingIterator();
                    Pipe curr;

                    while (it.hasNext()) {
                        curr = it.next();
                        sol.push(curr);
                        sol.removePipe(curr);
                    }

                    sol.addPipe(Pipe.L4);

                    return 1;
                }
            } else if(board.getPipe(p.goS()) == Pipe.I1) {
                if((sol.hasPipe(Pipe.I2) || sol.hasPipe(Pipe.CROSS)) && sol.hasPipe(Pipe.L4) && sol.hasPipe(Pipe.L1) && sol.hasPipe(Pipe.L3)) {
                    if (board.isEmpty(p.goE()) && board.isEmpty(p.goSE())) {
                        sol.addPipe(Pipe.L4);
                        sol.addPipe(Pipe.I1);

                        if (sol.hasPipe(Pipe.I2)) {
                            sol.push(Pipe.I2);
                            sol.removePipe(Pipe.I2);
                        } else {
                            sol.push(Pipe.CROSS);
                            sol.removePipe(Pipe.CROSS);
                        }
                        sol.push(Pipe.L4);
                        sol.push(Pipe.L1);
                        sol.removePipe(Pipe.L1);
                        sol.push(Pipe.L3);
                        sol.removePipe(Pipe.L3);

                        return 2;
                    }
                }
            }
            return 0;
        }
    },
    I2 {
        @Override
        public int apply(Board board, Point p, Solution sol, PipeBox pipeBox, Dir from) {
            if(board.getPipe(p.goE()) == Pipe.L1) {
                if ((sol.hasPipe(Pipe.L1) || sol.hasPipe(Pipe.CROSS)) && sol.hasPipe(Pipe.L2) && sol.hasPipe(Pipe.I1) && sol.hasPipe(Pipe.L4)) {
                    if (board.isEmpty(p.goS()) && board.isEmpty(p.goSE())) {
                        sol.addPipe(Pipe.I2);

                        sol.push(Pipe.L4);
                        sol.removePipe(Pipe.L4);
                        sol.push(Pipe.L2);
                        sol.removePipe(Pipe.L2);
                        sol.push(Pipe.L1);
                        if (sol.hasPipe(Pipe.I1)) {
                            sol.push(Pipe.I1);
                            sol.removePipe(Pipe.I1);
                        } else {
                            sol.push(Pipe.CROSS);
                            sol.removePipe(Pipe.CROSS);
                        }

                        return 2;
                    }
                }
            } else if(board.getPipe(p.goW()) == Pipe.L3) {
                if(sol.hasPipe(Pipe.L4) && sol.hasPipe(Pipe.L2) && (sol.hasPipe(Pipe.I1) || sol.hasPipe(Pipe.CROSS))) {
                    if(board.isEmpty(p.goN()) && board.isEmpty(p.goNW())) {
                        sol.addPipe(Pipe.I2);

                        sol.push(Pipe.L2);
                        sol.removePipe(Pipe.L2);
                        sol.push(Pipe.L4);
                        sol.removePipe(Pipe.L4);
                        sol.push(Pipe.L3);
                        if(sol.hasPipe(Pipe.I1)) {
                            sol.push(Pipe.L1);
                            sol.removePipe(Pipe.L1);
                        } else {
                            sol.push(Pipe.CROSS);
                            sol.removePipe(Pipe.CROSS);
                        }

                        return 2;
                    }
                }
            }
            return 0;
        }
    };

    public static int getHeuristic(Board board, Point p, Solution sol, PipeBox pipeBox, Dir from) {
        return values()[board.getPipe(p).ordinal()].apply(board, p, sol, pipeBox, from);
    }
}