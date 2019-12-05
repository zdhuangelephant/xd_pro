package com.xiaodou.server.pay.exception;

/**
 * @name @see com.xiaodou.server.pay.exception.CaPayfailException.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月8日
 * @description CA支付失败异常
 * @version 1.0
 */
public class CaPayfailException extends PaymentRuntimeException {
  /** serialVersionUID */
  private static final long serialVersionUID = -2227698267178122909L;

  public CaPayfailException(Throwable exception) {
    super(exception);
  }

  public CaPayfailException() {
    super();
  }

  public CaPayfailException(String msg) {
    super(msg);
  }

  public CaPayfailException(String msg, Throwable e) {
    super(msg, e);
  }
}
