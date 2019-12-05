package com.xiaodou.standard.protocol.exception;

import java.io.IOException;

import com.xiaodou.standard.protocol.MessageAble;

/**
 * @name @see com.xiaodou.standard.protocol.exception.TellException.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 倾诉异常
 * @version 1.0
 */
public class TellException extends CommunicateException {

  /** serialVersionUID */
  private static final long serialVersionUID = 5384563246291391458L;

  /** EXCEPTION_DESC_TMP 异常描述模板 */
  private static final String EXCEPTION_DESC_TMP =
      "TellException:[%s][MessageName:%s;MessageContent:%s]";

  /** DEFAULT_EXCEPTION_DESC 默认异常描述 */
  private static final String DEFAULT_EXCEPTION_DESC = "TellFail.";

  public TellException() {}

  public <T extends MessageAble> TellException(T message, IOException srcException) {
    this(DEFAULT_EXCEPTION_DESC, message, srcException);
  }

  protected <T extends MessageAble> TellException(String desc, T message, IOException srcException) {
    super(String.format(EXCEPTION_DESC_TMP, desc, message.uniqueMessageName(),
        message.messageContent()), message.from(), srcException);
  }
}
