package com.xiaodou.sms.common.enums;

public enum CascadeTemplateEnum {
  UnExistedResult(1,"短信模板没有对应的信息"),
  UnExistedChannelId(2,"没有对应的渠道号"),
  UnAbledChannelId(3,"没有可用的渠道号"),
  UnAbledTemplate(4,"没有可用的短信模板"),
  AddTaskFail(5,"生成短信失败");
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
  private CascadeTemplateEnum(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }
}
