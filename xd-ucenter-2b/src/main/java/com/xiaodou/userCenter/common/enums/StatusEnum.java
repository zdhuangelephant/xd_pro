package com.xiaodou.userCenter.common.enums;

public enum StatusEnum {
  Status_1("1", "待处理"), Status_2("2", "待审核"), Status_3("3", "待复核"), Status_4("4", "已解决");

  private String code;
  private String desc;

  private StatusEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
