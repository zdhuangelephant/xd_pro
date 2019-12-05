package com.xiaodou.standard.protocol.exception;

import java.io.IOException;

import com.xiaodou.standard.protocol.MessageAble;

/**
 * @name @see com.xiaodou.standard.protocol.exception.ListenException.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 倾听异常
 * @version 1.0
 */
public class ListenException extends CommunicateException {

  /** serialVersionUID */
  private static final long serialVersionUID = 4753527643916933452L;

  /** EXCEPTION_DESC_TMP 异常描述模板 */
  private static final String EXCEPTION_DESC_TMP =
      "ListenException:[%s][MessageName:%s;MessageContent:%s]";

  /** DEFAULT_EXCEPTION_DESC 默认异常描述 */
  private static final String DEFAULT_EXCEPTION_DESC = "ListenFail.";

  public ListenException() {}

  public <T extends MessageAble> ListenException(T message, IOException srcException) {
    super(String.format(EXCEPTION_DESC_TMP, DEFAULT_EXCEPTION_DESC, message.uniqueMessageName(),
        message.messageContent()), message.to(), srcException);
  }
}
