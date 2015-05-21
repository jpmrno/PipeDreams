package itba.eda.pipedreams.pipelogic;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

public class PipeBox implements Iterable<Pipe> {
	private int pipesSize;

	private Pipe[] allPipes = Pipe.values();
//	private int[] sizes;

	private final Map<Pipe, Integer> pipes; // Necessary?

	public PipeBox(int[] sizes) {
		Pipe[] pipesVec = Pipe.values();

		if(sizes.length != pipesVec.length) {
			throw new IllegalArgumentException();
		}

		pipes = new EnumMap<Pipe, Integer>(Pipe.class);
		for(int i = 0; i < pipesVec.length; i++) {
			if(sizes[i] < 0) {
				throw new IllegalArgumentException();
			}
			pipes.put(pipesVec[i], sizes[i]);
			pipesSize+= sizes[i];
		}
	}

	public int get(Pipe pipe) {
		return pipes.get(pipe);
	}

	public void add(Pipe pipe) {
		pipes.put(pipe, pipes.get(pipe) + 1);
		pipesSize++;
	}

	public void remove(Pipe pipe) {
		pipes.put(pipe, pipes.get(pipe) - 1);
		pipesSize--;
	}

	public boolean isEmpty() {
		if(pipesSize < 0) {
			throw new IllegalStateException();
		}

		return pipesSize <= 0;
	}

	public int size() {
		return pipesSize;
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
}
