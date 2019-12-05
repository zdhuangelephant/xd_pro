package com.xiaodou.amqp.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.xiaodou.summer.sceduling.concurrent.SummerTaskExecutor.SummerThreadFactoryBuilder;

public class RabbitDaemonThreadFactory implements ThreadFactory {
	private static final AtomicInteger poolNumber = new AtomicInteger(1);
	private ThreadFactory threadFactory;

	public RabbitDaemonThreadFactory(String poolName) {
		SummerThreadFactoryBuilder mThreadFactoryBuilder = new SummerThreadFactoryBuilder();
		mThreadFactoryBuilder.setNameFormat("jmsgPool-" + poolNumber.getAndIncrement() + "-" + poolName + "-thread-");
		mThreadFactoryBuilder.setDaemon(true);
		mThreadFactoryBuilder.setPriority(Thread.NORM_PRIORITY);
		this.threadFactory = mThreadFactoryBuilder.build();
	}

	public Thread newThread(Runnable r) {
		Thread t = threadFactory.newThread(r);
		t.setDaemon(true);
		if (t.getPriority() != Thread.NORM_PRIORITY)
			t.setPriority(Thread.NORM_PRIORITY);
		return t;
	}
}
