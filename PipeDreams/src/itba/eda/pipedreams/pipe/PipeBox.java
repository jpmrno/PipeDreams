package itba.eda.pipedreams.pipe;

import java.util.Iterator;

public class PipeBox implements Iterable<Pipe> {
	private int pipesSize;

	private Pipe[] allPipes = Pipe.values();
	private int[] sizes;

//	private final Map<Pipe, Integer> pipes; // Necessary?

//	public PipeBox(int[] sizes) {
//		Pipe[] pipesVec = Pipe.values();
//
//		if(sizes.length != pipesVec.length) {
//			throw new IllegalArgumentException();
//		}
//
//		pipes = new EnumMap<Pipe, Integer>(Pipe.class);
//		for(int i = 0; i < pipesVec.length; i++) {
//			if(sizes[i] < 0) {
//				throw new IllegalArgumentException();
//			}
//			pipes.put(pipesVec[i], sizes[i]);
//			pipesSize+= sizes[i];
//		}
//	}

	public PipeBox(int[] sizes) {
		if(sizes.length != allPipes.length) {
			throw new IllegalArgumentException();
		}

		this.sizes = new int[allPipes.length];

		for(int i = 0; i < allPipes.length; i++) {
			if(sizes[i] < 0) {
				throw new IllegalArgumentException();
			}
			this.sizes[i] = sizes[i];
			this.pipesSize += sizes[i];
		}
		this.pipesSize += + sizes[6];
	}

//	public int get(Pipe pipe) {
//		return pipes.get(pipe);
//	}
//
//	public void add(Pipe pipe) {
//		pipes.put(pipe, pipes.get(pipe) + 1);
//		pipesSize++;
//	}
//
//	public void remove(Pipe pipe) {
//		pipes.put(pipe, pipes.get(pipe) - 1);
//		pipesSize--;
//	}

	public Pipe getPipe(int i) {
		return allPipes[i];
	}

	public int getSize(int i) {
		return sizes[i];
	}

	public void addPipe(int i) {
		sizes[i]++;
	}

	public void removePipe(int i) {
		sizes[i]--;
	}

	public int length() {
		return allPipes.length;
	}

	@Override
	public Iterator<Pipe> iterator() {
		return new Iterator<Pipe>() {
			int i = 0;

			@Override
			public boolean hasNext() {
				return i < allPipes.length;
			}

			@Override
			public Pipe next() {
				return allPipes[i++];
			}
		};
	}

	public int getLongestPossible() {
		return pipesSize;
	}
}
