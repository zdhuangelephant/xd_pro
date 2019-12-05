package com.xiaodou.st.dashboard.constants.enums;

public enum StEnum {
  MAN("1", "男"), WOMAN("2", "女");

  private String code;
  private String name;

  private StEnum(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
