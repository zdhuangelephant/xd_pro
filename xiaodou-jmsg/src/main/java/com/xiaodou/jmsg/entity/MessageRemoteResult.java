package com.xiaodou.jmsg.entity;

import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * Created by zyp on 14-6-30.
 */
public class MessageRemoteResult {

  /**
   * 异常消息
   */
  private String ExceptionMessgage;

  /**
   * 响应码
   */
  private Integer ResponseCode;

  /** tag 外部消息Tag */
  private String tag;

  /**
   * 构造函数
   * 
   * @param omsRemoteResultType
   */
  public MessageRemoteResult(MessageRemoteResultType omsRemoteResultType) {
    this.ResponseCode = omsRemoteResultType.getResponseCode();
    this.ExceptionMessgage = omsRemoteResultType.getExceptionMessage();
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

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

  public enum MessageRemoteResultType {

    SUCCESS(0, "null"), FAIL(-1, "fail");

    private String exceptionMessage;
    private Integer responseCode;

    private MessageRemoteResultType(Integer responseCode, String exceptionMessgage) {
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

}
