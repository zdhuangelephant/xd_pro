package com.xiaodou.queue.exception;

import com.xiaodou.common.util.warp.FastJsonUtil;

public class UnSupportedMessageException extends RuntimeException {

  /** serialVersionUID */
  private static final long serialVersionUID = 4002896405779274063L;


  private static final String ERR_FORMAT = "不支持消息类型异常.%s";

  @SuppressWarnings("unused")
  private static class UnSupportedMessageInfo {
    private String errorInfo;
    private Object message;

    public String getErrorInfo() {
      return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
      this.errorInfo = errorInfo;
    }

    public Object getMessage() {
      return message;
    }

    public void setMessage(Object message) {
      this.message = message;
    }

    UnSupportedMessageInfo(String errorMessage, Object message) {
      this.errorInfo = errorMessage;
      this.message = message;
    }

    @Override
    public String toString() {
      return FastJsonUtil.toJson(this);
    }
  }

  public UnSupportedMessageException(String errorMessage, Object message) {
    super(String.format(ERR_FORMAT, new UnSupportedMessageInfo(errorMessage, message)));
  }
}
