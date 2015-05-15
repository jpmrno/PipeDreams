package itba.eda.pipedreams;

import java.io.*;
import java.util.regex.Pattern;

public class Main { // TODO: Ver las salidas
	private static final int ELEMS = 7;

	private static Method method;
	private static int approxTime;
	private static boolean progress;

	private static int rows;
	private static int columns;
	private static String[] board;
	private static int[] pipes;

	public static void main(String[] args) {
		if(args.length < 2 || args.length > 5) {
			System.err.println("Invalid number of arguments [2-4].");
			System.exit(1);
		}

		int i = 0;
		String fileName = args[i++];

		String methodString = args[i++];

		if(methodString.equalsIgnoreCase("approx")) {
			method = Method.APROX;
			try {
				approxTime = Integer.parseInt(args[i]);
				i++;
			} catch(ArrayIndexOutOfBoundsException e) { // TODO: MUY FEO? jajaja
				System.err.println("Missing aprox method argument.");
				System.exit(1);
			} catch(NumberFormatException e) {
				System.err.println("Argument: " + args[i] + " must be an integer.");
				System.exit(1);
			}
		} else if(methodString.equalsIgnoreCase("exact")) {
			method = Method.EXACT;
		} else {
			System.err.println("Invalid method: " + methodString + ".");
			System.exit(1);
		}

		if(i < args.length) {
			if(args[i].equalsIgnoreCase("progress")) {
				progress = true;
			} else {
				System.err.println("Illegal argument: " + args[i] + ".");
				System.exit(1);
			}
		}

		try {
			FileReader file = new FileReader(fileName);
			readFile(file);
		} catch(FileNotFoundException e) {
			System.err.println("Argument: " + args[0] + " must be a valid file.");
			System.exit(1);
		} catch(IOException e) {
			System.err.println("Error while trying to read: " + args[0] + ".");
			System.exit(1);
		}

		print();
	}

	private static void readFile(FileReader file) throws IOException { // TODO: sacar los System.exit()s
		try(BufferedReader br = new BufferedReader(file)) {
			String line = br.readLine();

			if(!getDimensions(line)) {
				System.err.println("Invalid file format.");
				System.exit(1);
			}

			board = new String[rows];
			for(int i = 0; i < rows; i++) {
				line = br.readLine();
				if(line == null || line.length() > columns) {
					System.err.println("Invalid file format.");
					System.exit(1);
				}

				board[i] = line;
			}

			pipes = new int[ELEMS];
			for(int i = 0; i < pipes.length; i++) {
				line = br.readLine();

				try {
					pipes[i] = Integer.parseInt(line);
				} catch(NumberFormatException e) {
					System.err.println("Invalid file format.");
					System.exit(1);
				}
			}

			line = br.readLine();
			if(line != null) {
				System.err.println("Invalid file format.");
				System.exit(1);
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
		for(String row : board) {
			System.out.println(row);
		}
		System.out.println();

		for(int i = 0; i < pipes.length; i++) {
			System.out.println("Pipe (#" + i + "): " + pipes[i] + ".");
		}
	}
}