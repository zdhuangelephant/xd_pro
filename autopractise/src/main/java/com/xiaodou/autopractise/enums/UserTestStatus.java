package com.xiaodou.autopractise.enums;

public enum UserTestStatus {
  EXAMING(1, "练习中"), BLOCKING(2, "等待试卷答案"), FINISH(-1, "已完成");
  UserTestStatus(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private Integer code;
  private String desc;

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public static UserTestStatus getStatusByCode(Integer code) {
    if (null == code) {
      return EXAMING;
    }
    if (EXAMING.code == code) {
      return EXAMING;
    }
    if (BLOCKING.code == code) {
      return BLOCKING;
    }
    if (FINISH.code == code) {
      return FINISH;
    }
    return EXAMING;
  }
}
