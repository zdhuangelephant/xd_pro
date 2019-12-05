package com.xiaodou.standard.protocol.exception;

import java.io.IOException;

import com.xiaodou.standard.protocol.TargetSocket;

/**
 * @name @see com.xiaodou.standard.protocol.exception.CommunicateException.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 通信异常
 * @version 1.0
 */
public class CommunicateException extends Exception {

  /** serialVersionUID */
  private static final long serialVersionUID = -964614170342178167L;

  /** EXCEPTION_DESC_TMP 异常描述模板 */
  private static final String EXCEPTION_DESC_TMP = "CommunicateException:[%s][%s]";

  /** DEFAULT_EXCEPTION_DESC 默认异常描述 */
  private static final String DEFAULT_EXCEPTION_DESC = "CommunicateFail.";

  public CommunicateException() {}


  public CommunicateException(TargetSocket socket, IOException srcException) {
    this(DEFAULT_EXCEPTION_DESC, socket, srcException);
  }

  protected CommunicateException(String desc, TargetSocket socket, Exception srcException) {
    super(String.format(EXCEPTION_DESC_TMP, desc, socket.toString()), srcException);
  }

  /**
   * @name @see com.xiaodou.standard.protocol.exception.CommunicateException.java
   * @CopyRright (c) 2018 by Corp.XiaodouTech
   * 
   * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
   * @date 2018年2月10日
   * @description 通信连接异常
   * @version 1.0
   */
  public static class ConnectException extends CommunicateException {

    /** serialVersionUID */
    private static final long serialVersionUID = 6537993279878605603L;

    /** DEFAULT_EXCEPTION_DESC 默认异常描述 */
    private static final String DEFAULT_EXCEPTION_DESC = "ConnectFail.";

    public ConnectException() {}

    public ConnectException(TargetSocket socket, IOException srcException) {
      super(DEFAULT_EXCEPTION_DESC, socket, srcException);
    }
  }
}
