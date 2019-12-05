package com.xiaodou.oms.vo.agent.response;

/**
 * Date: 2014/12/2
 * Time: 16:06
 *
 * @author Tian.Dong
 */
public class SendMessageResponse {
  /**
   * 异常消息
   */
  private String ExceptionMessgage;

  /**
   * 响应码
   */
  private Integer ResponseCode;


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
