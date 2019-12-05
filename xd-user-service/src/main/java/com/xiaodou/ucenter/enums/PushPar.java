package com.xiaodou.ucenter.enums;

public enum PushPar {
  UNTOKENCODE("retcode","20409"),UNTOKENMSG("retdesc", "token已失效，请重新登录");
  private String code;
  private String msg;

  private PushPar(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
