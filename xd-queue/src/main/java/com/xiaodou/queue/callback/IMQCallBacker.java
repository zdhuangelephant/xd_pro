package com.xiaodou.queue.callback;

import com.xiaodou.queue.enums.CallBackStatus;

/**
 * @name @see com.xiaodou.queue.callback.IMQCallBacker.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月28日
 * @description MQ消息执行回调接口
 * @version 1.0
 */
public interface IMQCallBacker<T> {

  /**
   * 成功回调
   * 
   * @param message 消息体
   */
  public void onSuccess(T message);

  /**
   * 失败回调
   * 
   * @param staus 回调状态
   * @param message 消息体
   */
  public void onFail(CallBackStatus staus, T message);

}
