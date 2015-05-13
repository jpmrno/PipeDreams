public interface Pipe {
	boolean canItFlow(Dir from);

	Dir flow(Dir from);
}