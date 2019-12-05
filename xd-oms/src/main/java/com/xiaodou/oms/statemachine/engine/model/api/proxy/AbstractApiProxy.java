package com.xiaodou.oms.statemachine.engine.model.api.proxy;

import com.xiaodou.oms.statemachine.Context;
import com.xiaodou.oms.statemachine.engine.model.api.BaseAPI;
import com.xiaodou.oms.statemachine.engine.model.api.proxy.extension.ICondition;
import com.xiaodou.oms.statemachine.engine.model.api.proxy.extension.IException;
import com.xiaodou.oms.statemachine.engine.model.api.proxy.extension.ITimeOut;

/**
 * <p>
 * 抽象API代理基类
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月19日
 * @param <T>
 */
public abstract class AbstractApiProxy<T extends BaseAPI> implements IApiProxy {

  /**
   * API
   */
  protected T api;

  /**
   * 条件拓展点
   */
  protected Class<? extends ICondition> onCondition;

  /**
   * 超时拓展点
   */
  protected Class<? extends ITimeOut> onTimeOut;

  /**
   * 异常拓展点
   */
  protected Class<? extends IException> onException;

  public Class<? extends ITimeOut> getOnTimeOut() {
    return onTimeOut;
  }

  @SuppressWarnings("unchecked")
  public void setOnTimeOut(String onTimeOut) throws ClassNotFoundException {
    Class<? extends ITimeOut> timeOut = (Class<? extends ITimeOut>) Class.forName(onTimeOut);
    this.onTimeOut = timeOut;
  }

  public Class<? extends IException> getOnException() {
    return onException;
  }

  @SuppressWarnings("unchecked")
  public void setOnException(String onException) throws ClassNotFoundException {
    Class<? extends IException> exception =
        (Class<? extends IException>) Class.forName(onException);
    this.onException = exception;
  }

  public Class<? extends ICondition> getOnCondition() {
    return onCondition;
  }

  @SuppressWarnings("unchecked")
  public void setOnCondition(String onCondition) throws ClassNotFoundException {
    Class<? extends ICondition> condition = (Class<? extends ICondition>) Class.forName(onCondition);
    this.onCondition = condition;
  }

  @SuppressWarnings("unchecked")
  public T getApi() {
    return api;
  }

  public void setApi(T api) {
    this.api = api;
  }

  public AbstractApiProxy(T api) {
    this.api = api;
  }

  public AbstractApiProxy() {}

  /**
   * API执行方法
   * 
   * @param context 上下文
   * @throws Exception 异常
   */
  public abstract Object execute(Context context) throws Exception;

  public Object invoke(Context context) throws Exception {
    boolean canExcute = true;
    if (null != this.onCondition) {
      canExcute &= this.onCondition.newInstance().onCondition(context);
    }
    if (canExcute) {
      try {
        return execute(context);
      } catch (Exception e) {
        if (null != this.onException) {
          this.onException.newInstance().onException(context);
        } else {
          throw e;
        }
      }
    }
    return null;
  }
}
