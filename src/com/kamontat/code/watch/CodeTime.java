package com.kamontat.code.watch;

import java.util.concurrent.TimeUnit;

/**
 * @author kamontat
 * @version 1.0
 * @since 1/15/2017 AD - 2:35 AM
 */
public class CodeTime {
	
	/**
	 * @param runnable
	 * 		running code
	 * @param delay
	 * 		duration of timeout
	 * @param timeUnit
	 * 		unit of duration
	 */
	public static void setTimeout(Runnable runnable, int delay, TimeUnit timeUnit) {
		new Thread(() -> {
			try {
				Thread.sleep(timeUnit.toMillis(delay));
				runnable.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
}
