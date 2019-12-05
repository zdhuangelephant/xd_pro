package com.xiaodou.jmsg.server.scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConfScheduledExecutor {
	private ScheduledExecutorService scheduExec;
	public long start;

	ConfScheduledExecutor() {
		this.scheduExec = Executors.newSingleThreadScheduledExecutor();
		this.start = System.currentTimeMillis();
	}

	public void timer() {
		scheduExec.scheduleAtFixedRate(new Runnable() {
			public void run() {
				ConfTask.excute();
			}
		}, 5, 5, TimeUnit.MINUTES);
	}
}