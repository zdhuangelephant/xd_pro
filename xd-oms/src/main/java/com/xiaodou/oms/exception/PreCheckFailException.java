package com.xiaodou.oms.exception;

public class PreCheckFailException extends OrderRuntimeException {
  
  public PreCheckFailException(Throwable exception) {
    super(exception);
  }

  public PreCheckFailException() {
    super("[前置检查失败]");
  }

  public PreCheckFailException(String msg) {
    super("[前置检查失败]"+msg);
  }

  public PreCheckFailException(String msg, Throwable e) {
    super(msg,e);
  }

}
