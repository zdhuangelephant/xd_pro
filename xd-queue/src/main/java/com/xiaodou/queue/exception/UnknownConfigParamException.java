package com.xiaodou.queue.exception;

/**
 * @name @see com.xiaodou.queue.client.exception.UnknownConfigParamException.java 
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月23日
 * @description 未知配置项异常
 * @version 1.0
 */
public class UnknownConfigParamException extends RuntimeException {

  /** serialVersionUID */
  private static final long serialVersionUID = 2044479454363453857L;

  public UnknownConfigParamException() {
    super();
  }

  public UnknownConfigParamException(String message) {
    super(message);
  }
}
