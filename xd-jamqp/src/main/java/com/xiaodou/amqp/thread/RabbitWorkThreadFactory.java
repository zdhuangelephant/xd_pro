package com.xiaodou.amqp.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.xiaodou.summer.sceduling.concurrent.SummerTaskExecutor.SummerThreadFactoryBuilder;

public class RabbitWorkThreadFactory implements ThreadFactory {
	/** serialVersionUID */
	private static final AtomicInteger poolNumber = new AtomicInteger(1);
	private ThreadFactory threadFactory;

	public RabbitWorkThreadFactory(String poolName) {
		SummerThreadFactoryBuilder mThreadFactoryBuilder = new SummerThreadFactoryBuilder();
		mThreadFactoryBuilder.setNameFormat("jmsgPool-" + poolNumber.getAndIncrement() + "-" + poolName + "-thread-");
		mThreadFactoryBuilder.setDaemon(false);
		mThreadFactoryBuilder.setPriority(Thread.NORM_PRIORITY);
		this.threadFactory = mThreadFactoryBuilder.build();
	}

	public Thread newThread(Runnable r) {
		Thread t = threadFactory.newThread(r);
		if (t.isDaemon())
			t.setDaemon(false);
		if (t.getPriority() != Thread.NORM_PRIORITY)
			t.setPriority(Thread.NORM_PRIORITY);
		return t;
	}
}
