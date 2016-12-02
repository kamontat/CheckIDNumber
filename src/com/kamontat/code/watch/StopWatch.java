package com.kamontat.code.watch;

/**
 * clock object that can run timer of the program
 * <p>
 *
 * @author kamontat
 * @version 2.1
 * @since 9/22/2016 AD - 10:57 AM
 */
public class StopWatch {
	private long startTime;
	private long stopTime;
	
	public void start() {
		startTime = System.currentTimeMillis();
	}
	
	public void stop() {
		stopTime = System.currentTimeMillis();
	}
	
	public long getElapsed() {
		return (stopTime - startTime);
	}
	
	@Override
	public String toString() {
		String ms = "(%dms)";
		String s = "(%.2fs)";
		long interval = getElapsed();
		
		// convert to second
		if (interval >= 1000) {
			return String.format("%.2fs", interval / 1000.0);
		} else {
			return String.format("%dms", interval);
		}
	}
}