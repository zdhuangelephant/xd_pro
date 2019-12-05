package com.xiaodou.logmonitor.util;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.logmonitor.prop.RabbitMqProp;

public class RabbitMqUtil {
	private static final String TASK_QUEUE_NAME = RabbitMqProp.getParams("ququeName");
	private static final String HOST = RabbitMqProp.getParams("host");
	private static final String USERNAME = RabbitMqProp.getParams("userName");
	private static final String PASSWORD = RabbitMqProp.getParams("passWord");
	private static final String PORT = RabbitMqProp.getParams("port");
	public static QueueingConsumer consumer;
	public static Channel channel;
	public static void init() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		factory.setUsername(USERNAME);
		factory.setPassword(PASSWORD);
		factory.setPort(Integer.valueOf(PORT));
		try {
			Connection connection = factory.newConnection();

			channel = connection.createChannel();
			// 指定队列持久化
			channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

			// 指定该消费者同时只接收一条消息
			channel.basicQos(1);
			consumer = new QueueingConsumer(channel);
			// 打开消息应答机制
			channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
		} catch (IOException | ShutdownSignalException
				| ConsumerCancelledException e) {
			// TODO Auto-generated catch block
			LoggerUtil.error("RabbitMq连接失败", e);
		}
	}
}