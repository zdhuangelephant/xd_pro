package com.xiaodou.amqp.connectpool.receiverdispatch;

import java.util.concurrent.ExecutorService;

import com.xiaodou.amqp.config.RabbitConfig;
import com.xiaodou.amqp.connectpool.RabbitConnectPool;
import com.xiaodou.amqp.connectpool.RabbitReceiveProxy;
import com.xiaodou.amqp.exception.AmqpClientException;
import com.xiaodou.amqp.messagentity.InternalMessageEntity;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledThreadPoolExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerTaskExecutor.SummerThreadFactoryBuilder;

/**
 * 消息接收器,开启一个单线程用于监听消息
 */
public class MessageReceiver implements Runnable {

	private final Class<? extends MessageListener> listenerClass;
	private final  String queueName;
	private final int qos;
	private final boolean autoAck;
	private final int parallel;
	private final ExecutorService executorService;

	private boolean stop = true;
	private boolean running = false;
	private Thread t;

	/**
	 * 构造函数
	 * 
	 * @param listenerClass
	 *            监听类
	 * @param queueName
	 *            监听队列名称
	 * @param parallel
	 *            处理消息的并行线程数量
	 * @param qos
	 *            一次获取消息的数量
	 * @param autoAck
	 *            true表示采用需要autoAck模式，false则需要回调ack/reject
	 */
	public MessageReceiver(Class<? extends MessageListener> listenerClass, String queueName, int parallel, int qos,
			boolean autoAck) {
		this.listenerClass = listenerClass;
		this.queueName = queueName;
		this.qos = qos;
		this.autoAck = autoAck;
		if (parallel <= 0) {
			this.parallel = Runtime.getRuntime().availableProcessors() * 2;
		} else {
			this.parallel = parallel;

		}
		SummerThreadFactoryBuilder mThreadFactoryBuilder = new SummerThreadFactoryBuilder();
		mThreadFactoryBuilder.setCorePoolSize(this.parallel);
		mThreadFactoryBuilder.setNameFormat("Q-" + queueName + "-Work");
		mThreadFactoryBuilder.setDaemon(false);
		mThreadFactoryBuilder.setPriority(Thread.NORM_PRIORITY);
		executorService = new SummerScheduledThreadPoolExecutor(this.parallel, mThreadFactoryBuilder.build());
	}

	/**
	 * 开启轮询获取队列中消息的任务线程
	 */
	public void start() {
		if (running) {
			return;
		}
		stop = false;
		t = new Thread(this, "Q-" + this.queueName + "-Dispatch");
		t.start();
	}

	/**
	 * 停止接受消息,关闭线程
	 */
	public void stop() {
		running = false;
		if (t != null && t.isAlive()) {
			t.interrupt();
		}
	}

	@Override
	public void run() {
		running = true;
		ListenTask();
		running = false;
	}

	/**
	 * 销毁创建的连接
	 */
	@Override
	protected void finalize() throws Throwable {
		stop();
		if (t != null && t.isAlive()) {
			t.interrupt();
		}
		super.finalize();
	}

	/**
	 * 线程内执行的轮询获取指定队列中的消息任务
	 */
	private void ListenTask() {
		RabbitReceiveProxy proxy = null;
		while (!stop) {
			proxy = checkProxy(proxy);
			try {
				MessageProcessTask task = getNextTask(proxy);
				if (task != null) {
					executorService.execute(task);
				}
			} catch (Exception e) {
				proxy.setNotAvailable();
				LoggerUtil.error("Communicate with rabbitMQ server error: ", e);
				try {
					Thread.sleep(RabbitConfig.getInstance().getReceiveFreeInterval());
				} catch (Exception ex) {
					LoggerUtil.error("Sleep for ReceiveFreeInterval failed: ", ex);
				}
			}
		}
	}

	private RabbitReceiveProxy checkProxy(RabbitReceiveProxy proxy) {
		if (proxy == null || !proxy.isAvailable()) {
			LoggerUtil.debug("Connect rabbitMQ server...");
			RabbitReceiveProxy usableProxy = proxy;
			while (true) {
				try {
					if (usableProxy != null) {
						usableProxy.tryDispose();
					}
					usableProxy = RabbitConnectPool.getInstance().getReceiveConnection(this.qos);
					usableProxy.beginConsume(this.queueName, this.autoAck);
					LoggerUtil.debug("Connect rabbitMQ server success.");
					return usableProxy;
				} catch (Exception e) {
					LoggerUtil.error("Connect rabbitMQ server failed: ", e);
					if (usableProxy != null) {
						usableProxy.setNotAvailable();
					}
					try {
						Thread.sleep(RabbitConfig.getInstance().getReceiveFreeInterval());
					} catch (Exception ex) {
						LoggerUtil.error("Sleep for ReceiveFreeInterval failed: ", ex);
					}
				}
			}
		}
		return proxy;
	}

	private MessageProcessTask getNextTask(RabbitReceiveProxy proxy) throws AmqpClientException {
		MessageProcessTask task = null;
		int useCount = proxy.increaseUseCount();
		if (useCount > 0 && useCount <= this.parallel) {
			InternalMessageEntity message = proxy.next(queueName, RabbitConfig.getInstance().getReceiveFreeInterval());
			if (message != null) {
				task = new MessageProcessTask(this.listenerClass, this.queueName, this.autoAck, message, proxy);
			}
		}
		proxy.decreaseUseCount();

		return task;
	}

}
