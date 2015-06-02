package itba.eda.pipedreams.solver.engine;

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
		if(isRunning) {
			runningTime = System.currentTimeMillis() - runningTime;
			isRunning = false;
		}
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

	public static int convertToMiliseconds(int min) {
		return min * 60 * 1000;
	}
}
