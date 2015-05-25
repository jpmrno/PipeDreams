package itba.eda.pipedreams.uielements;

import itba.eda.pipedreams.solver.pipe.BasicPipeBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InfoPane extends HBox {
	public InfoPane(BasicPipeBox pipeBox) {
		super();

		this.setPadding(new Insets(20));
		this.setSpacing(10);
	}

	private class Tile extends VBox {
		private Label label;
		private ImageView imageView;

		public Tile(Label label, ImageView imageView) {
			super();

			this.label = label;
			this.label.setAlignment(Pos.CENTER);
			this.imageView = imageView;
			this.getChildren().addAll(imageView, label);
		}

		public Label getLabel() {
			return label;
		}

		public ImageView getImageView() {
			return imageView;
		}
	}
}
