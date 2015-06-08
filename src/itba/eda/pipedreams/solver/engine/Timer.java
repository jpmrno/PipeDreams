package itba.eda.pipedreams.solver.engine;

public class Timer {
	private long runningTime;
	private boolean isRunning;
	
	
	public Timer(){
		runningTime = 0;
		isRunning = false;
	}
	
	public void start() {
		if(!isRunning) {
			runningTime = System.currentTimeMillis();
			isRunning = true;
		}
	}
	
	public void stop() {
		if(isRunning) {
			runningTime = System.currentTimeMillis() - runningTime;
			isRunning = false;
		}
	}

	public void reset() {
		runningTime = 0;
		isRunning = false;
	}

	public long getRunningTime() {
		if(isRunning) {
			return System.currentTimeMillis() - runningTime;
		} else {
			return runningTime;
		}
	}

	public void printRunningTime() {
		if(isRunning) {
			System.out.println("Running time is: " + (System.currentTimeMillis() - runningTime) / 1000 + "s");
		} else {
			System.out.println("Running time was: " + runningTime / 1000 + "s");
		}
	}

	public static int convertToMilliseconds(int min) {
		return min * 60 * 1000;
	}
}
