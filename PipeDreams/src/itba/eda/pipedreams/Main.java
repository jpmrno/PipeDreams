package itba.eda.pipedreams;

import itba.eda.pipedreams.solver.PDSolverArguments;
import itba.eda.pipedreams.solver.board.Board;
import itba.eda.pipedreams.uielements.BoardPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
	private static PDSolverArguments arguments;

	private static Stage mainStage;
	private static Scene mainScene;

	private static Pane mainPane; // TODO: Pane or HBox?
	//private static InfoPane infoPane;
	private static BoardPane boardPane;
	
	public static void main(String[] args) {
		arguments = new PDSolverArguments(args); // TODO: In Main class?
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		mainStage = primaryStage;
		setUserAgentStylesheet(STYLESHEET_MODENA);
		mainStage.setTitle("Pipe.it");
		mainStage.setResizable(false);
		mainStage.centerOnScreen();
		mainStage.setOnCloseRequest(event -> System.exit(0));

		mainPane = new HBox();
		boardPane = new BoardPane(new Board(arguments.getBoardFile()));

		mainPane.getChildren().addAll(boardPane);
		mainScene = new Scene(mainPane);
		mainStage.setScene(mainScene);

		primaryStage.show();
	}
}
