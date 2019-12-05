package com.xiaodou.queue.base;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.callback.IMQCallBacker;

/**
 * @name @see com.xiaodou.queue.base.AbstractMethod.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月22日
 * @description 部分通用主干方法实现(带回调)
 * @version 1.0
 */
public abstract class AbstractMethodWithCallBack<M, C> {
  public boolean check() {
    return true;
  }

  public void excute0(M message, IMQCallBacker<C> callback) {
    try {
      if (check()) {
        domain(message, callback);
      }
    } catch (Exception e) {
      onExceptionCallBack(message, e, callback);
    }
  }

  public void excute0(List<M> message, IMQCallBacker<List<C>> callback) {
    try {
      if (check()) {
        domain(message, callback);
      }
    } catch (Exception e) {
      onExceptionCallBack(message, e, callback);
    }
  }

  public abstract void domain(M message, IMQCallBacker<C> callback) throws Exception;

  public abstract void domain(List<M> message, IMQCallBacker<List<C>> callback) throws Exception;

  protected void onExceptionCallBack(List<M> message, Throwable t, IMQCallBacker<List<C>> callback) {
    LoggerUtil.error(
        String.format("批量消息处理异常.Message : %s", FastJsonUtil.toJson(message.toString())), t);
    onException(t, message, callback);
  }

  protected void onExceptionCallBack(M message, Throwable t, IMQCallBacker<C> callback) {
    LoggerUtil.error(String.format("消息处理异常.Message : %s", FastJsonUtil.toJson(message.toString())),
        t);
    onException(t, message, callback);
  }

  public abstract void onException(Throwable t, List<M> message, IMQCallBacker<List<C>> callback);

  public abstract void onException(Throwable t, M message, IMQCallBacker<C> callback);
}
