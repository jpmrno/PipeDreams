package itba.eda.pipedreams.enginelogic;

public class Timer {
	private long runningTime;
	private boolean isRunning;
	
	
	public Timer(){
		runningTime = 0;
		isRunning = false;
	}
	
	public void startClock() {
		if(!isRunning) {
			runningTime = System.currentTimeMillis();
			isRunning = true;
		} else {
			System.out.println("Timer has already started");
		}
	}
	
	public void stopClock(){
		runningTime = System.currentTimeMillis() - runningTime;
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
			System.out.println("Running time is: " + runningTime + "ms");
		} else {
			System.out.println("Running time was: " + runningTime + "ms");
		}
	}
}
