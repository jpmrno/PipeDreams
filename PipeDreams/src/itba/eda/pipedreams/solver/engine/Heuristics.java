package itba.eda.pipedreams.solver.engine;

import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.Board;
import itba.eda.pipedreams.solver.pipe.Pipe;
import itba.eda.pipedreams.solver.pipe.PipeBox;

public enum Heuristics implements Heuristic {
    L1 {
        @Override
        public int apply(Board board, Point p, Solution sol, PipeBox pipeBox) {
            if (pipeBox.getSize(Pipe.CROSS.ordinal()) > 0 && pipeBox.getSize(Pipe.L4.ordinal()) > 0 && pipeBox.getSize(Pipe.L2.ordinal()) > 0) {
                if (board.isEmpty(new Point(p.getRow() + 1, p.getColumn())) && board.isEmpty(new Point(p.getRow(), p.getColumn() + 1))
                        && board.isEmpty(new Point(p.getRow() + 1, p.getColumn() + 1))) {
                    sol.push(Pipe.CROSS);
                    sol.getAuxPipeBox()[Pipe.CROSS.ordinal()]--;
                    sol.push(Pipe.L4);
                    sol.getAuxPipeBox()[Pipe.L4.ordinal()]--;
                    sol.push(Pipe.L1);
                    sol.removePipe(Pipe.L2);
                    sol.getAuxPipeBox()[Pipe.L2.ordinal()]--;
                    sol.removePipe(Pipe.CROSS);

                    return 2;
                }
            }
            return 0;
        }
    },
    L2 {
        @Override
        public int apply(Board board, Point p, Solution sol, PipeBox pipeBox) {
            Point prevRow = new Point(p.getRow() - 1, p.getColumn());
            Point prevCol = new Point(p.getRow(), p.getColumn() - 1);
            Point prevRowPrevCol = new Point(p.getRow() - 1, p.getColumn() - 1);
            if(board.getPipe(prevRow) == Pipe.I1) {
                if(sol.hasPipe(Pipe.L3) && sol.hasPipe(Pipe.L1) && sol.hasPipe(Pipe.L1)) {
                    if(board.isEmpty(prevCol) && board.isEmpty(prevRowPrevCol)) {
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
        @Override
        public int apply(Board board, Point p, Solution sol, PipeBox pipeBox) {


            return 0;
        }
    },
    L4 {
        @Override
        public int apply(Board board, Point p, Solution sol, PipeBox pipeBox) {
            Point nextRow = new Point(p.getRow() + 1, p.getColumn());
            Point nextRowNextCol = new Point(p.getRow() + 1, p.getColumn() + 1);

            if(board.getPipe(nextRow) == Pipe.I1) {
                if((sol.hasPipe(Pipe.I2) || sol.hasPipe(Pipe.CROSS)) && sol.hasPipe(Pipe.L4) && sol.hasPipe(Pipe.L1) && sol.hasPipe(Pipe.L3)) {
                    if (board.isEmpty(nextRow) && board.isEmpty(nextRowNextCol)) {
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
                        sol.removePipe(Pipe.L4);
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
        public int apply(Board board, Point p, Solution sol, PipeBox pipeBox) {
            Point nextCol = new Point(p.getRow(), p.getColumn() + 1);
            Point prevCol = new Point(p.getRow(), p.getColumn() - 1);
            Point nextRow = new Point(p.getRow() + 1, p.getColumn());
            Point prevRow = new Point(p.getRow() - 1, p.getColumn());
            Point prevRowPrevCol = new Point(p.getRow() - 1, p.getColumn() - 1);
            Point nextRowNextCol = new Point(p.getRow() + 1, p.getColumn() + 1);

            if(board.getPipe(nextCol) == Pipe.L1) {
                if ((sol.hasPipe(Pipe.L1) || sol.hasPipe(Pipe.CROSS)) && sol.hasPipe(Pipe.L2) && sol.hasPipe(Pipe.I1) && sol.hasPipe(Pipe.L4)) {
                    if (board.isEmpty(nextRow) && board.isEmpty(nextRowNextCol)) {
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
            } else if(board.getPipe(prevCol) == Pipe.L3) {
                if(sol.hasPipe(Pipe.L4) && sol.hasPipe(Pipe.L2) && (sol.hasPipe(Pipe.I1) || sol.hasPipe(Pipe.CROSS))) {
                    if(board.isEmpty(prevRow) && board.isEmpty(prevRowPrevCol)) {
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

    public static int getHeuristic(Board board, Point p, Solution sol, PipeBox pipeBox) {
        return values()[board.getPipe(p).ordinal()].apply(board, p, sol, pipeBox);
    }
}