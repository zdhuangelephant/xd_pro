package com.xiaodou.server.pay.exception;

/**
 * @name @see com.xiaodou.server.pay.exception.DcPayfailException.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月8日
 * @description Dc支付失败异常
 * @version 1.0
 */
public class DcPayfailException extends PaymentRuntimeException {
  /** serialVersionUID */
  private static final long serialVersionUID = -2227698267178122909L;

  public DcPayfailException(Throwable exception) {
    super(exception);
  }

  public DcPayfailException() {
    super();
  }

  public DcPayfailException(String msg) {
    super(msg);
  }

  public DcPayfailException(String msg, Throwable e) {
    super(msg, e);
  }
}
