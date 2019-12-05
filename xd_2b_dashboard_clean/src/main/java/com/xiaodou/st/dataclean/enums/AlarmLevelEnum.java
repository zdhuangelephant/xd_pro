package com.xiaodou.st.dataclean.enums;

public enum AlarmLevelEnum {

  AlarmLevel_1("1", "初级"),AlarmLevel_2("2", "中级"),AlarmLevel_3("3", "高级 ");
  private String code;
  private String desc;

  private AlarmLevelEnum(String code, String desc) {
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
