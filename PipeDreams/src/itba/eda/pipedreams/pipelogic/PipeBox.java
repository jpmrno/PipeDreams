package itba.eda.pipedreams.pipelogic;

public class PipeBox {
	private final static int ITEMS_SIZE = PipeFactory.getPipeTypesSize();
	private int[] items;
	
	public PipeBox() {
		items = new int[ITEMS_SIZE];
	}
	
	public void setItem(int pipeType, int count) {
		items[pipeType] = count;
	}
	
	public boolean hasItem(int pipeType) {
		return items[pipeType] > 0;
	}
	
	public Pipe getItem(int pipeType) {
		return PipeFactory.getPipe(pipeType);
	}
	
	public void remove(int pipeType) {
		items[pipeType]--;
	}
	
	public void add(int pipeType) {
		items[pipeType]++;
	}
	
	public int getSize() {
		return ITEMS_SIZE;
	}
}
