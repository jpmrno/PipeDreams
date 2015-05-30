package itba.eda.pipedreams.solver.engine;

import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.Board;
import itba.eda.pipedreams.solver.pipe.Pipe;
import itba.eda.pipedreams.solver.pipe.PipeBox;

public enum Heuristics implements Heuristic {
    H1 {
        @Override
        public int apply(Board board, Point p, Solution sol, PipeBox pipeBox) {
            if(board.getPipe(p).equals(Pipe.L1)) {
                if (pipeBox.getSize(Pipe.CROSS.ordinal()) > 0 && pipeBox.getSize(Pipe.L4.ordinal()) > 0 && pipeBox.getSize(Pipe.L2.ordinal()) > 0) {
                    if (board.isEmpty(new Point(p.getRow() + 1, p.getColumn())) && board.isEmpty(new Point(p.getRow(), p.getColumn() + 1))
                            && board.isEmpty(new Point(p.getRow() + 1, p.getColumn() + 1))) {
                        sol.push(Pipe.CROSS);
                        sol.getAuxPipeBox()[Pipe.CROSS.ordinal()]--;
                        sol.push(Pipe.L4);
                        sol.getAuxPipeBox()[Pipe.L4.ordinal()]--;
                        sol.push(Pipe.L1);
                        sol.push(Pipe.L2);
                        sol.getAuxPipeBox()[Pipe.L2.ordinal()]--;
                        sol.push(Pipe.CROSS);

                        return 2;
                    }
                }
            }
            return 0;
        }
    },
    H2 {
        @Override
        public int apply(Board board, Point p, Solution sol, PipeBox pipeBox) {
            if(board.getPipe(p).equals(Pipe.I2)) {
                if(board.getPipe(new Point(p.getRow(), p.getColumn() + 1)).equals(Pipe.L1)) {
                    if (pipeBox.getSize(Pipe.L1.ordinal()) > 0 && pipeBox.getSize(Pipe.L2.ordinal()) > 0 && pipeBox.getSize(Pipe.I1.ordinal()) > 0 && pipeBox.getSize(Pipe.L4.ordinal()) > 0) {
                        if (board.isEmpty(new Point(p.getRow() + 1, p.getColumn())) && board.isEmpty(new Point(p.getRow() + 1, p.getColumn() + 1))) {
                            sol.push(Pipe.L4);
                            sol.push(Pipe.L2);
                            sol.push(Pipe.L1);
                            sol.push(Pipe.I1);
                        }
                    }
                }
            }
        }
    };

    public static int getHeuristic(Board board, Point p, Solution sol, PipeBox pipeBox) {
        int skip;

        for(Heuristic h : values()) {
            skip = h.apply(board, p, sol, pipeBox);
            if(skip != 0) {
                return skip;
            }
        }
        return 0;
    }
}