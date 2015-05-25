package itba.eda.pipedreams.solver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class PDSolverArguments { // TODO: Static?
	private static final int ELEMS = 7; // TODO: Read from backend

	private Method method;
	private int approxTime;

	private boolean progress;

	private int rows;
	private int columns;
	private String[] boardFile;

	private int[] pipes; // TODO: List<Integer> items = Collections.unmodifiableList(Arrays.asList(0,1,2,3));

	public PDSolverArguments(String[] args) { // TODO: OK?
		if(args.length < 2 || args.length > 5) {
			throw new IllegalArgumentException("Invalid number of arguments [2-4].");
		}

		int i = 0;
		String fileName = args[i++]; // after: i = 1

		String methodString = args[i++]; // after: i = 2
		if(!getMethod(methodString)) {
			throw new IllegalArgumentException("Invalid method: " + methodString + ".");
		}

		if(method == Method.APROX) {
			if(i < args.length && getTime(args[i])) {
				i++;
			} else {
				throw new IllegalArgumentException("Aprox method argument missing or invalid. Must be a number.");
			}
		}

		if(i < args.length && !hasProgress(args[i++])) {
			throw new IllegalArgumentException("Invalid argument: " + args[i - 1] + ".");
		}

		if(i < args.length) {
			throw new IllegalArgumentException("Invalid arguments.");
		}

		try { // TODO: Ugly?
			FileReader file = new FileReader(fileName);
			readFile(file);
		} catch(FileNotFoundException e) {
			throw new IllegalArgumentException("File not found.");
		} catch(IOException e) {
			throw new IllegalArgumentException("");
		} catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid file format.");
		}
 	}

	public Method getMethod() {
		return method;
	}

	public int getTime() {
		return approxTime;
	}

	public boolean withProgress() {
		return progress;
	}

	public String[] getBoardFile() {
		return boardFile;
	}

	public int[] getPipeSizes() {
		return pipes;
	}

	private boolean getMethod(String methodString) {
		if(methodString.equalsIgnoreCase("approx")) {
			method = Method.APROX;
			return true;
		}

		if(methodString.equalsIgnoreCase("exact")) {
			method = Method.EXACT;
			return true;
		}

		return false;
	}

	private boolean getTime(String arg) {
		try {
			approxTime = Integer.parseInt(arg);
		} catch(NumberFormatException e) {
			return false;
		}

		return approxTime > 0;

	}

	private boolean hasProgress(String arg) {
		if(arg.equalsIgnoreCase("progress")) {
			progress = true;
		}

		return false;
	}

	private void readFile(FileReader file) throws IOException {
		try(BufferedReader br = new BufferedReader(file)) {
			String line = br.readLine();

			if(!getDimensions(line)) {
				throw  new IllegalArgumentException();
			}

			boardFile = new String[rows];
			for(int i = 0; i < rows; i++) {
				line = br.readLine();
				if(line == null || line.length() > columns) {
					throw  new IllegalArgumentException();
				}

				boardFile[i] = line;
			}

			pipes = new int[ELEMS];
			for(int i = 0; i < pipes.length; i++) {
				line = br.readLine();

				try {
					pipes[i] = Integer.parseInt(line);
				} catch(NumberFormatException e) {
					throw  new IllegalArgumentException();
				}
			}

			line = br.readLine();
			if(line != null) {
				throw  new IllegalArgumentException();
			}

		}
	}

	private boolean getDimensions(String line) {
		if(line == null || !Pattern.matches("^\\d+,\\d+$", line)) {
			return false;
		}

		String[] first = line.split(",");
		rows = Integer.valueOf(first[0]);
		columns = Integer.valueOf(first[1]);

		return true;
	}

	private void print() { // TODO: remove it, for debugging proposes only
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
}