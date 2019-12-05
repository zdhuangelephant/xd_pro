package com.xiaodou.amqp.connectpool.receiverdispatch;

/**
 * 外部需要实现的消息监听接口,返回消息体
 */
public interface MessageListener {
	boolean receive(String msg, String queueName) throws Exception;
}
