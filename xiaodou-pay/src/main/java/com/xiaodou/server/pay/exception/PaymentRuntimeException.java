package com.xiaodou.server.pay.exception;

/**
 * @name @see com.xiaodou.server.pay.exception.PaymetnException.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月8日
 * @description 支付系统异常
 * @version 1.0
 */
public class PaymentRuntimeException extends RuntimeException {
  /** serialVersionUID */
  private static final long serialVersionUID = -2227698267178122909L;

  public PaymentRuntimeException(Throwable exception) {
    super(exception);
  }

  public PaymentRuntimeException() {
    super();
  }

  public PaymentRuntimeException(String msg) {
    super(msg);
  }

  public PaymentRuntimeException(String msg, Throwable e) {
    super(msg, e);
  }
}
