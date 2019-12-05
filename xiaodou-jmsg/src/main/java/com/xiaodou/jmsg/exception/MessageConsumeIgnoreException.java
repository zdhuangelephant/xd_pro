package com.xiaodou.jmsg.exception;

public class MessageConsumeIgnoreException extends RuntimeException {

  /** 唯一UID */
  private static final long serialVersionUID = -210077523883119645L;
  public MessageConsumeIgnoreException(Throwable exception) {
    super(exception);
  }

  public MessageConsumeIgnoreException() {
    super();
  }

  public MessageConsumeIgnoreException(String msg) {
    super(msg);
  }

  public MessageConsumeIgnoreException(String msg, Throwable e) {
    super(msg,e);
  }
}
