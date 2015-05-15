package itba.eda.pipedreams.enginelogic;

public class Timer {
	
	private long running_time;
	
	
	public Timer(){
		running_time = 0;
	}
	
	public void startClock(){
		running_time = System.currentTimeMillis();
	}
	
	public long stopClock(){
		long final_time = System.currentTimeMillis();
		return final_time - running_time;
	}
}
