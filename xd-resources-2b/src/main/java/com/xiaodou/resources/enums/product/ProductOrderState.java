package com.xiaodou.resources.enums.product;

public enum ProductOrderState {
  Init(0, "Init", "新单");
  ProductOrderState(Integer name, String code, String desc) {
    this.name = name;
    this.code = code;
    this.desc = desc;
  }

  private Integer name;
  private String code;
  private String desc;

  public Integer getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }

}
