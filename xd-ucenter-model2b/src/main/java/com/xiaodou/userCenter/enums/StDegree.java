package com.xiaodou.userCenter.enums;

public enum StDegree {
  BENKE("1", "本科"), ZHUANKE("2", "专科");
  private String code;
  private String name;

  private StDegree(String code, String name) {
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
