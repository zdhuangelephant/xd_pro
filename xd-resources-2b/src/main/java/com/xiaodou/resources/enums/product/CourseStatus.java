package com.xiaodou.resources.enums.product;

public enum CourseStatus {
  coming("0", "未开课"), progress("1", "进行中"), end("2", "已结束 ");

  CourseStatus(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  private String code;
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
}
