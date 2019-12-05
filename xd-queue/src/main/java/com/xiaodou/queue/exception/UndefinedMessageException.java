package com.xiaodou.queue.exception;

/**
 * @name @see com.xiaodou.queue.client.exception.UndefinedMessageException.java 
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月23日
 * @description 消息未定义异常
 * @version 1.0
 */
public class UndefinedMessageException extends RuntimeException {

  /** serialVersionUID */
  private static final long serialVersionUID = -2687987519681445452L;

  public UndefinedMessageException() {
    super();
  }

  public UndefinedMessageException(String message) {
    super(message);
  }


}
