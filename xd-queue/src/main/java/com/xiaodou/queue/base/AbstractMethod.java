package com.xiaodou.queue.base;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.queue.base.AbstractMethod.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月22日
 * @description 部分通用主干方法实现(不带回调)
 * @version 1.0
 */
public abstract class AbstractMethod<M> {
  public boolean check() {
    return true;
  }

  public void excute0(M message) {
    try {
      if (check()) {
        domain(message);
      }
    } catch (Exception e) {
      onExceptionCallBack(message, e);
    }
  }

  public void excute0(List<M> message) {
    try {
      if (check()) {
        domain(message);
      }
    } catch (Exception e) {
      onExceptionCallBack(message, e);
    }
  }

  public abstract void domain(M message) throws Exception;

  public abstract void domain(List<M> message) throws Exception;

  protected void onExceptionCallBack(List<M> message, Throwable t) {
    LoggerUtil.error(
        String.format("批量消息处理异常.Message : %s", FastJsonUtil.toJson(message.toString())), t);
    onException(t, message);
  }

  protected void onExceptionCallBack(M message, Throwable t) {
    LoggerUtil.error(String.format("消息处理异常.Message : %s", FastJsonUtil.toJson(message.toString())),
        t);
    onException(t, message);
  }

  public abstract void onException(Throwable t, M message);

  public abstract void onException(Throwable t, List<M> message);
}
