package com.xiaodou.mqcr.model;

public enum MessageStatus {

  INIT(0, "初始化"), SUCCESS(1, "消费成功"), FAIL(2, "消费失败"), TIMEOUT(3, "超时"), OTHER(4, "其它");

  private Integer code;
  private String name;

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private MessageStatus(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

}
