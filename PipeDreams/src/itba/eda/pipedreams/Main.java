package itba.eda.pipedreams;

import itba.eda.pipedreams.solver.basic.PDSolverArgs;
import itba.eda.pipedreams.solver.PDSolver;
import itba.eda.pipedreams.uielements.BoardPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
	private static PDSolverArgs arguments;

	private static Stage mainStage;
	private static Scene mainScene;

	private static ScrollPane mainPane; // TODO: Pane or HBox?
	private static BoardPane boardPane;
	
	public static void main(String[] args) {
		arguments = new PDSolverArgs(args); // TODO: In Main class?
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		mainStage = primaryStage;
		setUserAgentStylesheet(STYLESHEET_MODENA);
		mainStage.setTitle("Pipe.it");
		mainStage.setResizable(true);
		mainStage.centerOnScreen();
		mainStage.setOnCloseRequest(event -> System.exit(0));

		mainPane = new ScrollPane();
		boardPane = new BoardPane(arguments.getRows(), arguments.getColumns());

		mainPane.setContent(boardPane);
		PDSolver solver = new PDSolver(arguments, boardPane);

		mainScene = new Scene(mainPane);
		mainStage.setScene(mainScene);
		primaryStage.show();

		while(!primaryStage.isShowing()) {
			;
		}

		solver.start();
	}
}
