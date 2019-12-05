package com.xiaodou.server.mapi.util;

import com.xiaodou.server.mapi.web.filter.CheckUserStatusFilter.CheckTokenEnum;


public class CheckTokenExceptionWrapper {

  private static final ThreadLocal<CheckTokenExceptionWrapper> localContext =
      new ThreadLocal<CheckTokenExceptionWrapper>();

  private CheckTokenExceptionWrapper() {}

  private CheckTokenEnum errValue;

  public CheckTokenEnum getErrValue() {
    return errValue;
  }

  public CheckTokenEnum getAndRemove() {
    CheckTokenEnum tempErrValue = errValue;
    errValue = null;
    return tempErrValue;
  }

  public void setErrValue(CheckTokenEnum errValue) {
    this.errValue = errValue;
  }

  public static CheckTokenExceptionWrapper getWrapper() {
    CheckTokenExceptionWrapper wrapper = localContext.get();
    if (null == wrapper) {
      wrapper = new CheckTokenExceptionWrapper();
      localContext.set(wrapper);
    }
    return wrapper;
  }

  public static void cleanWrapper() {
    localContext.remove();
  }

  static {
    initWrapper();
  }

  private static void initWrapper() {
    getWrapper();
  }

}
