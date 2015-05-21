package itba.eda.pipedreams;

import itba.eda.pipedreams.enginelogic.Engine;
import itba.eda.pipedreams.tablelogic.Board;

import java.io.*;
import java.util.regex.Pattern;

public class Main { // TODO: Ver las salidas // TODO: sacar o modificar los System.exit()s
	
	private static final int ELEMS = 7; // TODO: Leerlo del back

	private static Method method;
	private static int approxTime;
	private static boolean progress;

	private static int rows;
	private static int columns;
	private static String[] boardFile;
	private static int[] pipes;

	public static void main(String[] args) {
		
		if(args.length < 2 || args.length > 5) {
			error("Invalid number of arguments [2-4].");
		}

		int i = 0;
		String fileName = args[i++];

		String methodString = args[i++];

		if(i < args.length) {
			getMethod(methodString, args[i]);
			if(approxTime != 0) {
				i++;
			}
			if(i < args.length) {
				hasProgress(args[i]);
			}
		}

		openFile(fileName);

		Board board = new Board(rows, columns, boardFile);
		Engine engine = new Engine(board, Method.EXACT, pipes);
		engine.start();
	}

	private static void getMethod(String methodString, String arg) {
		if(methodString.equalsIgnoreCase("approx")) {
			method = Method.APROX;
			try {
				approxTime = Integer.parseInt(arg);
			} catch(ArrayIndexOutOfBoundsException e) {
				error("Missing aprox method argument.");
			} catch(NumberFormatException e) {
				error("Argument: " + arg + " must be an integer.");
			}

			if(approxTime <= 0) {
				error("Invalid aprox time.");
			}
		} else if(methodString.equalsIgnoreCase("exact")) {
			method = Method.EXACT;
		} else {
			error("Invalid method: " + methodString + ".");
		}
	}

	private static void hasProgress(String arg) {
		if(arg.equalsIgnoreCase("progress")) {
			progress = true;
		} else {
			error("Illegal argument: " + arg + ".");
		}
	}

	private static void openFile(String fileName) {
		try {
			FileReader file = new FileReader(fileName);
			readFile(file);
		} catch(FileNotFoundException e) {
			error("Argument: " + fileName + " must be a valid file.");
		} catch(IOException e) {
			error("Error while trying to read: " + fileName + ".");
		}
	}

	private static void readFile(FileReader file) throws IOException {
		try(BufferedReader br = new BufferedReader(file)) {
			String line = br.readLine();

			if(!getDimensions(line)) {
				error("Invalid file format.");
			}

			boardFile = new String[rows];
			for(int i = 0; i < rows; i++) {
				line = br.readLine();
				if(line == null || line.length() > columns) {
					error("Invalid file format.");
				}

				boardFile[i] = line;
			}

			pipes = new int[ELEMS];
			for(int i = 0; i < pipes.length; i++) {
				line = br.readLine();

				try {
					pipes[i] = Integer.parseInt(line);
				} catch(NumberFormatException e) {
					error("Invalid file format.");
				}
			}

			line = br.readLine();
			if(line != null) {
				error("Invalid file format.");
			}

		}
	}

	private static boolean getDimensions(String line) {
		if(line == null || !Pattern.matches("^\\d+,\\d+$", line)) {
			return false;
		}

		String[] first = line.split(",");
		rows = Integer.valueOf(first[0]);
		columns = Integer.valueOf(first[1]);

		return true;
	}

	private static void print() {
		System.out.println(method == Method.EXACT ? "Metodo EXACT" : "Metodo APROX");

		if(approxTime != 0) {
			System.out.println("Tiempo: " + approxTime + ".");
		}

		if(progress) {
			System.out.println("Con progress.");
		}

		System.out.println();
		System.out.println("Filas: " + rows + " , Columns: " + columns + ".");
		System.out.println();
		for(String row : boardFile) {
			System.out.println(row);
		}
		System.out.println();

		for(int i = 0; i < pipes.length; i++) {
			System.out.println("Pipe (#" + i + "): " + pipes[i] + ".");
		}
	}

	private static void error(String msj) {
		System.err.println(msj);
		System.exit(1);
	}
}