package itba.eda.pipedreams.solver.engine;

import itba.eda.pipedreams.solver.basic.Point;
import itba.eda.pipedreams.solver.board.Board;
import itba.eda.pipedreams.solver.board.Dir;
import itba.eda.pipedreams.solver.pipe.PipeBox;

public interface Heuristic {
    int apply(Board board, Point p, Solution sol, PipeBox pipeBox, Dir from);
}
