package com.xiaodou.st.dataclean.enums;

public enum PretreatmentEnum {

  AccountNoPretreat("1","不做处理"),DeviceNoPretreat("2","不做处理"),AccountClosure("3", "账号封停"), 
  DeviceClosure("4", "设备封停"),AccountDeviceClosure("5","账号设备封停"),ResponseNothing("6", "空");
  PretreatmentEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private String code;
  private String desc;

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
