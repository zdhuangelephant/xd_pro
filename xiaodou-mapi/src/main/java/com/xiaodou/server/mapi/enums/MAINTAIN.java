package com.xiaodou.server.mapi.enums;

public enum MAINTAIN {
  UPGRADE("10000","系統升級中");
  
  private String code;
  private String msg;

  private MAINTAIN(String code,String msg) {
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
