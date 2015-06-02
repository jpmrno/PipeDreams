package itba.eda.pipedreams.solver.pipe;

import java.util.Random;

public abstract class BasicPipeBox implements Cloneable, Iterable<Pipe> {
	public abstract int getSize(Pipe pipe);

	public abstract boolean hasPipe(Pipe pipe);

	public abstract boolean hasPipe(Pipe pipe, int amount);

	public abstract void addOnePipe(Pipe pipe);

	public abstract void removeOnePipe(Pipe pipe);

	public abstract int length();

	public static int[] shufflePipes() {
		int[] map = new int[Pipe.values().length];

		for(int i = 0; i < map.length; i++) {
			map[i] = i;
		}

		Random rand = new Random();
		for(int i = map.length - 1; i > 0; i--) {
			int index = rand.nextInt(i + 1);
			int aux = map[index];
			map[index] = map[i];
			map[i] = aux;
		}

		return map;
	}

	public abstract BasicPipeBox clone();
}
