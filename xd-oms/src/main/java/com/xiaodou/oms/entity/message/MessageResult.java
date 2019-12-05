package com.xiaodou.oms.entity.message;

import com.xiaodou.oms.constant.order.MessageResultType;

public class MessageResult {
  /**
   * 异常消息
   */
  private String ExceptionMessgage;

  /**
   * 响应码
   */
  private Integer ResponseCode;

  /**
   * 构造函数
   *
   * @param omsRemoteResultType
   */
  public MessageResult(MessageResultType messageResultType) {
    this.ResponseCode = messageResultType.getResponseCode();
    this.ExceptionMessgage = messageResultType.getExceptionMessage();
  }

  public String getExceptionMessgage() {
    return ExceptionMessgage;
  }

  public void setExceptionMessgage(String exceptionMessgage) {
    ExceptionMessgage = exceptionMessgage;
  }

  public Integer getResponseCode() {
    return ResponseCode;
  }

  public void setResponseCode(Integer responseCode) {
    ResponseCode = responseCode;
  }

  public void appendRetdesc(String retdesc) {
    this.ExceptionMessgage += retdesc;
  }
}
