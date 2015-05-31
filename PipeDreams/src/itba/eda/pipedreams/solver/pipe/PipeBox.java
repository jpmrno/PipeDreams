package itba.eda.pipedreams.solver.pipe;

import java.util.Iterator;
import java.util.Random;

public class PipeBox implements Iterable<Pipe>, BasicPipeBox { // TODO: return BasicPipe?
	private int originalSize;

	private static Pipe[] pipes = Pipe.values();
	private int[] sizes;

	public PipeBox(int[] sizes) {
		if(sizes.length != pipes.length) {
			throw new IllegalArgumentException();
		}

		this.sizes = new int[pipes.length];

		for(int i = 0; i < pipes.length; i++) {
			if(sizes[i] < 0) {
				throw new IllegalArgumentException();
			}
			this.sizes[i] = sizes[i];
			this.originalSize += sizes[i];
		}
		this.originalSize += sizes[6];
	}

	@Override
	public Pipe getPipe(int i) {
		return pipes[i];
	}

	@Override
	public int getSize(int i) {
		return sizes[i];
	}

	public void addOnePipe(int i) {
		sizes[i]++;
	}

	public void removeOnePipe(int i) {
		if(sizes[i] == 0) {
			return;
		}
		sizes[i]--;
	}

	@Override
	public int length() {
		return pipes.length;
	}

	public int getLongestPossible() {
		return originalSize;
	}

	@Override
	public Iterator<Pipe> iterator() {
		return new Iterator<Pipe>() {
			int i = 0;

			@Override
			public boolean hasNext() {
				return i < pipes.length;
			}

			@Override
			public Pipe next() {
				return pipes[i++];
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public static int[] shufflePipes() {
		int[] map = new int[pipes.length];

		for(int i=0; i < map.length; i++) {
			map[i] = i;
		}
		Random rand = new Random();

		for (int i = map.length - 1; i > 0; i--) {
			int index = rand.nextInt(i + 1);
			int a = map[index];
			map[index] = map[i];
			map[i] = a;
		}

		return map;
	}
}
