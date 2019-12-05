package com.xiaodou.st.dashboard.constants.enums;

public enum CheckStatusEnum {

  CheckStatus_1("1", "待处理", "添加处理结果"), CheckStatus_2("2", "待审核", "添加处理结果并提交审核"), CheckStatus_3("3",
      "待处理", "审核未通过"), CheckStatus_4("4", "待复核", "审核通过并提交复核"), CheckStatus_5("5", "待处理", "复核未通过"), CheckStatus_6(
      "6", "已解决", "复核通过"),CheckStatus_7("7", "已解决", "添加处理结果"),CheckStatus_8("8", "已解决", "审核通过");
  private String code;
  private String statusDesc;
  private String desc;


  private CheckStatusEnum(String code, String statusDesc, String desc) {
    this.code = code;
    this.statusDesc = statusDesc;
    this.desc = desc;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getStatusDesc() {
    return statusDesc;
  }

  public void setStatusDesc(String statusDesc) {
    this.statusDesc = statusDesc;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

}
