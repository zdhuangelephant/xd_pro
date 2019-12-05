package com.xiaodou.queue.consumer;

import java.util.List;

import com.xiaodou.queue.base.AbstractMethodWithCallBack;
import com.xiaodou.queue.callback.IMQCallBacker;

/**
 * @name @see com.xiaodou.queue.consumer.AbstractConsumer.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月22日
 * @description 消息拉取消费者主干方法
 * @version 1.0
 */
public abstract class AbstractConsumer<M, C> extends AbstractMethodWithCallBack<M, C>
    implements
      IConsumer<M, C> {

  /** serialVersionUID */
  private static final long serialVersionUID = -5722235088701001203L;

  @Override
  public void consumer(M message, IMQCallBacker<C> callback) {
    excute0(message, callback);
  }

  @Override
  public void consumerList(List<M> messageList, IMQCallBacker<List<C>> callback) {
    excute0(messageList, callback);
  }

}
