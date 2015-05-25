package itba.eda.pipedreams.uielements;

import itba.eda.pipedreams.solver.GameDisplay;
import itba.eda.pipedreams.solver.GameBoard;
import itba.eda.pipedreams.solver.board.Point;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class BoardPane extends ScrollPane implements GameDisplay {

	private final GridPane pane;
	private final int rows;
	private final int columns;
	private final GameBoard board;

	public BoardPane(GameBoard board) {
		super();

		this.rows = board.getRowSize();
		this.columns = board.getColumnSize();
		this.board = board;
		this.pane = new GridPane();
		this.setContent(pane);
		this.rePaint();
	}

	@Override
	public void rePaint() {
		Point point = new Point(0, 0);
		for(int row = 0; row < rows; row++) {
			for(int column = 0; column < columns; column++) {
				point.setRow(row);
				point.setColumn(column);
				ImageView imageView = new ImageView(GameTile.fromString(board.getRepresentation(point)));
				imageView.setCache(true);
				pane.add(imageView, column, row);
			}
		}
	}

}
