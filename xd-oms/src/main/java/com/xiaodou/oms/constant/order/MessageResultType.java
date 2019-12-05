package com.xiaodou.oms.constant.order;

public enum MessageResultType {

  SUCCESS(0,"null"),
  FAIL(-1,"失败。");

  private String exceptionMessage;
  private Integer responseCode;

  private MessageResultType(Integer responseCode,String exceptionMessgage) {
      this.responseCode = responseCode;
      this.exceptionMessage = exceptionMessgage;
  }

  public String getExceptionMessage() {
      return exceptionMessage;
  }

  public Integer getResponseCode() {
      return responseCode;
  }
  
}
