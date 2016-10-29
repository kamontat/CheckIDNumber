package com.kamontat.code.object;

import java.util.*;

/**
 * clock object that can run timer of the program
 * <p>
 *
 * @author kamontat
 * @version 2.1
 * @since 9/22/2016 AD - 10:57 AM
 */
public class StopWatch {
	private String label;
	private long startTime;
	private long stopTime;
	private ArrayList<Long> history = new ArrayList<>();
	
	public StopWatch(String label) {
		this.label = label;
	}
	
	public void start() {
		startTime = System.currentTimeMillis();
	}
	
	public void stop() {
		stopTime = System.currentTimeMillis();
	}
	
	public long getElapsed() {
		history.add(stopTime - startTime);
		return (stopTime - startTime);
	}
}
