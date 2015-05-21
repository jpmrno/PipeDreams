package itba.eda.pipedreams.pipelogic;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

public class PipeBox implements Iterable<Pipe> {
//	private Pipe[] allPipes;
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
		}
	}

	public int get(Pipe pipe) {
		return pipes.get(pipe);
	}

	public void add(Pipe pipe) {
		pipes.put(pipe, pipes.get(pipe) + 1);
	}

	public void remove(Pipe pipe) {
		pipes.put(pipe, pipes.get(pipe) - 1);
	}

	@Override
	public Iterator<Pipe> iterator() {
		return new Iterator<Pipe>() {
			int i = 0;

			@Override
			public boolean hasNext() {
				return i < Pipe.values().length;
			}

			@Override
			public Pipe next() {
				return Pipe.values()[i++];
			}
		};
	}
}
