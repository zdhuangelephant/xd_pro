package com.xiaodou.queue.consumer;

import java.io.Serializable;
import java.util.List;

import com.xiaodou.queue.callback.IMQCallBacker;

/**
 * @name @see com.xiaodou.queue.consumer.IConsumer.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月22日
 * @description 消息拉取消费者
 * @version 1.0
 */
public interface IConsumer<M, T> extends Serializable, Cloneable {

  /**
   * 消费单个消息
   * 
   * @param message
   */
  public void consumer(M message, IMQCallBacker<T> callback);

  /**
   * 消费一组消息
   * 
   * @param messageList
   */
  public void consumerList(List<M> messageList, IMQCallBacker<List<T>> callback);

}
