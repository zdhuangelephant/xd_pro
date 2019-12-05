package com.xiaodou.oms.exception;

public class OrderRuntimeException extends RuntimeException {

  public OrderRuntimeException(Throwable exception) {
    super(exception);
  }

  public OrderRuntimeException() {
    super();
  }

  public OrderRuntimeException(String msg) {
    super(msg);
  }

  public OrderRuntimeException(String msg, Throwable e) {
    super(msg,e);
  }

}
