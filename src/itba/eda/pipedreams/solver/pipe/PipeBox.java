package itba.eda.pipedreams.solver.pipe;

import java.util.Iterator;

public class PipeBox extends BasicPipeBox { // TODO: return BasicPipe?
	private int originalSize;

	private static final Pipe[] pipes = Pipe.values();
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

	public Pipe getPipe(int pipe) {
		return pipes[pipe];
	}

	@Override
	public int getSize(Pipe pipe) {
		return sizes[pipe.ordinal()];
	}

	public boolean hasPipe(Pipe pipe) {
		return sizes[pipe.ordinal()] > 0;
	}

	public boolean hasPipe(Pipe pipe, int amount) {
		return sizes[pipe.ordinal()] >= amount;
	}

	@Override
	public void addOnePipe(Pipe pipe) {
		sizes[pipe.ordinal()]++;
	}

	@Override
	public void removeOnePipe(Pipe pipe) {
		if(sizes[pipe.ordinal()] == 0) {
			return;
		}
		sizes[pipe.ordinal()]--;
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

	@Override
	public PipeBox clone() {
		return new PipeBox(sizes);
	}
}