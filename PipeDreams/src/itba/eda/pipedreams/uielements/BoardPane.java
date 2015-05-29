package itba.eda.pipedreams.uielements;

import itba.eda.pipedreams.solver.basic.BoardDisplay;
import itba.eda.pipedreams.solver.board.BasicBoard;
import itba.eda.pipedreams.solver.basic.GameBoard;
import itba.eda.pipedreams.solver.basic.Point;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

public class BoardPane extends Canvas implements BoardDisplay { // TODO: Make this a Canvas & put in a ScrollPane
	private int i = 0;

	private final int rows;
	private final int columns;
	private GameBoard board;

	public BoardPane(int rows, int columns) {
		super(columns * GameTile.width, rows * GameTile.height);

		this.rows = rows;
		this.columns = columns;

		this.snapshot(new SnapshotParameters(), new WritableImage(200, 200));
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

	public void saveAsPng() {

		System.out.println("ENTRE");

		WritableImage image = this.snapshot(new SnapshotParameters(), null);

		// TODO: probably use a file chooser here
		File file = new File("imgs/chart" + i++ + ".png");

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		} catch (IOException e) {
			System.out.println("ERROR FEO");
		}
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
