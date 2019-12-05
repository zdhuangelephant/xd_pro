package com.xiaodou.logmonitor.spout;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import backtype.storm.topology.base.BaseRichSpout;

public abstract class BaseSpout extends BaseRichSpout {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  private Queue<Object> messageQueue = new ConcurrentLinkedQueue<Object>();

  public Queue<Object> getMessageQueue() {
    return messageQueue;
  }

  public void setMessageQueue(Queue<Object> messageQueue) {
    this.messageQueue = messageQueue;
  }

  @Override
  public void ack(Object msgId) {
    messageQueue.remove(msgId);
    System.out
        .println("------------------------------------------ack------------------------------------");
  }

  @Override
  public void fail(Object msgId) {
    messageQueue.remove(msgId);
    System.out
        .println("------------------------------------------fail------------------------------------");
  }
}
