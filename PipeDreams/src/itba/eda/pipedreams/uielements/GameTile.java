package itba.eda.pipedreams.uielements;

import javafx.scene.image.Image;

public enum GameTile {
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
