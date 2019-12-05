package com.xiaodou.goeasy.publish;

public enum GoEasyErrorCode
{
  PARAMETER_ERROR, UNAUTHORIZED, UNREACHABLE_TIMEOUT, SERVER_ERROR, UNKNOWN_ERROR;

  private int code;
  private String content;

  public int code()
  {
    return this.code;
  }

  public String content() {
    return this.content;
  }
}
