package com.kamontat.code.watch;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author kamontat
 * @version 1.0
 * @since 1/15/2017 AD - 3:13 AM
 */
class CodeTimerTest {
	@Test
	void setTimeout() {
		TimeUnit tu = TimeUnit.SECONDS;
		int during = 10; // second
		long first = StopWatch.getCurrentTime(tu);
		CodeTime.setTimeout(() -> {
			long second = StopWatch.getCurrentTime(tu);
			
			org.junit.jupiter.api.Assertions.assertEquals(during, (second - first));
		}, during, tu);
	}
}