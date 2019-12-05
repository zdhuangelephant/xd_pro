package com.xiaodou.goeasy.publish;

public class GoEasyError {
  private int code;
  private String content;

  public GoEasyError(int code, String content) {
    this.code = code;
    this.content = content;
  }

  public int getCode() {
    return this.code;
  }

  public String getContent() {
    return this.content;
  }
}
