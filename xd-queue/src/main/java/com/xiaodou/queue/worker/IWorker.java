package com.xiaodou.queue.worker;

import java.io.Serializable;
import java.util.List;

import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.IMessage;

/**
 * 执行者接口 外部系统实现该接口 最终实现自己的业务逻辑 worker对象里面，不能定义可变类变量参数！！！！
 * 
 * @author bing.cheng
 * 
 */
public interface IWorker<F extends IMessage, T> extends Serializable, Cloneable {

  /**
   * 业务逻辑执行
   * 
   * @param message
   */
  public void excute(F message, IMQCallBacker<T> callback);

  /**
   * 业务逻辑批处理执行
   * 
   * @param message
   */
  public void excute(List<F> message, IMQCallBacker<List<T>> callback);

}
