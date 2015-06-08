package itba.eda.pipedreams;

import itba.eda.pipedreams.solver.basic.PDSolver;
import itba.eda.pipedreams.solver.basic.PDSolverArgs;
import itba.eda.pipedreams.uielements.BoardPane;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	private static PDSolverArgs arguments;

	public static void main(String[] args) {
		arguments = new PDSolverArgs(args); // TODO: In Main class?
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		Stage mainStage = primaryStage;
		setUserAgentStylesheet(STYLESHEET_MODENA);
		mainStage.setTitle("Pipe.it");
		mainStage.setResizable(true);
		mainStage.centerOnScreen();
		mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});

		ScrollPane mainPane = new ScrollPane();
		BoardPane boardPane = new BoardPane(arguments.getRows(), arguments.getColumns());

		mainPane.setContent(boardPane);
		PDSolver solver = new PDSolver(arguments, boardPane);

		Scene mainScene = new Scene(mainPane);
		mainStage.setScene(mainScene);
		primaryStage.show();

		solver.start();
	}
}
