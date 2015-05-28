package itba.eda.pipedreams.uielements;

import itba.eda.pipedreams.solver.basic.BoardDisplay;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.basic.GameBoard;
import itba.eda.pipedreams.solver.basic.Point;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Observable;

public class BoardPane extends Canvas implements BoardDisplay { // TODO: Make this a Canvas & put in a ScrollPane
	private final int rows;
	private final int columns;
	private GameBoard board;

	public BoardPane(int rows, int columns) {
		super(columns * GameTile.width, rows * GameTile.height);

		this.rows = rows;
		this.columns = columns;
	}

	private void paint() {
		if(board == null) {
			throw new NullPointerException("No board set.");
		}

		Point point = new Point(0, 0);
		GraphicsContext gc = this.getGraphicsContext2D();

		for(int row = 0; row < rows; row++) {
			for(int column = 0; column < columns; column++) {
				point.setRow(row);
				point.setColumn(column);
				gc.clearRect(column * GameTile.width, row * GameTile.height, GameTile.width, GameTile.height);
				gc.drawImage(GameTile.fromString(board.getRepresentation(point)), column * GameTile.width, row * GameTile.height);
			}
		}

	}

	@Override
	public void setBoard(BasicBoard board) {
		if(this.board != null) {
			throw new IllegalStateException("Board was already set.");
		}

		this.board = board;
		paint();
	}

	@Override
	public void update(Observable o, Object arg) {
		Platform.runLater(this::paint);
	}

	private enum GameTile { // TODO: Here? Better way? Check!
		L1("/img/L1.png"),
		L2("/img/L2.png"),
		L3("/img/L3.png"),
		L4("/img/L4.png"),
		I1("/img/I1.png"),
		I2("/img/I2.png"),
		CROSS("/img/CROSS.png"),
		EMPTY("/img/EMPTY.png"),
		WALL("/img/WALL.png"),
		START_N("/img/START_N.png"),
		START_S("/img/START_S.png"),
		START_E("/img/START_E.png"),
		START_W("/img/START_W.png");

		public static final int width = 50;
		public static final int height = 50;

		private final Image image;

		GameTile(String imageUrl) {
			image = new Image(imageUrl/*, true*/); // Load image in background
		}

		public Image getImage() {
			return image;
		}

		public static Image fromString(String str) {
			switch(str) {
				case "1":
					return L1.image;
				case "2":
					return L2.image;
				case "3":
					return L3.image;
				case "4":
					return L4.image;
				case "5":
					return I1.image;
				case "6":
					return I2.image;
				case "7":
					return CROSS.image;
				case ".":
					return EMPTY.image;
				case "#":
					return WALL.image;
				case "N":
					return START_N.image;
				case "S":
					return START_S.image;
				case "E":
					return START_E.image;
				case "W":
					return START_W.image;
				default:
					return null;
			}
		}
	}

}

//	public void paint(Board board) {
//		Point point = new Point(0, 0);
//		GraphicsContext gc = pane.getGraphicsContext2D();
//
//		for(int row = 0; row < rows; row++) {
//			for(int column = 0; column < columns; column++) {
//				point.setRow(row);
//				point.setColumn(column);
//				gc.drawImage(GameTile.fromString(board.getRepresentation(point)), column * 50, row * 50);
//			}
//		}
//	}
