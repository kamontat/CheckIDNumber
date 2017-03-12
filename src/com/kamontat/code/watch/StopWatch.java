package com.kamontat.code.watch;

import java.util.concurrent.TimeUnit;

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
	
	/**
	 * this method will give you current time in other unit (nanosecond, microsecond, millisecond, second, minutes) <br>
	 * if you enter param more than it's example The Default is <b>millisecond</b>
	 *
	 * @param timeUnit
	 * @return
	 */
	public static long getCurrentTime(TimeUnit timeUnit) {
		switch (timeUnit) {
			case NANOSECONDS:
				return System.nanoTime();
			case MICROSECONDS:
				return getCurrentTime(TimeUnit.NANOSECONDS) / 1000;
			case MILLISECONDS:
				return System.currentTimeMillis();
			case SECONDS:
				return getCurrentTime(TimeUnit.MILLISECONDS) / 1000;
			case MINUTES:
				return getCurrentTime(TimeUnit.SECONDS) / 60;
			case HOURS:
				return getCurrentTime(TimeUnit.MINUTES) / 60;
			case DAYS:
				return getCurrentTime(TimeUnit.HOURS) / 24;
			default:
				return getCurrentTime(TimeUnit.SECONDS);
		}
	}
	
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