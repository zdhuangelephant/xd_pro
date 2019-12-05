package com.xiaodou.st.dashboard.constants.enums;

public enum LearnTypeEnum {

  /* 类型（pk(11做题，12解析),闯关（21做题，22解析），31修炼，41错题,51每日一练） */
  LearnType_11("11", "pk做题"), LearnType_12("12", "pk解析"), LearnType_21("21", "闯关做题"), LearnType_22(
      "22", "闯关解析"), LearnType_31("31", "修炼"), LearnType_41("41", "错题"), LearnType_51("51", "每日一练"),
      LearnType_61("61", "期末测试");
  private String code;
  private String desc;

  private LearnTypeEnum(String code, String desc) {
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
