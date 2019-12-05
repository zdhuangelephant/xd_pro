package com.xiaodou.oms.exception;

/**
 * <p>未知Transition异常</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月21日
 */
public class UnknownTransitionException extends OrderRuntimeException {
  public UnknownTransitionException() {
    super();
  }

  public UnknownTransitionException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnknownTransitionException(String message) {
    super(message);
  }

  public UnknownTransitionException(Throwable cause) {
    super(cause);
  }
}
