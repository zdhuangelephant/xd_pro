package com.xiaodou.ucerCenter.agent.enums;

public enum CheckTokenEnum {

  READ_TIMED_OUT("00003","请求超时！"),
  SEND_HTTP_FAIL("00004","http异常,访问请求超时！");
  
  /**code 结果码*/
  private String code;
  /**msg 提示信息*/
  private String msg;

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

  CheckTokenEnum(String code, String msg) {
    this.code= code;
    this.msg=msg;
  }
  
}
