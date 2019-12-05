package com.xiaodou.common.http;

/**
 * 自定义HTTP状态
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-3-18
 */
public enum HttpResultStatus {
  /**
   * 自定义HTTP状态-初始状态
   */
  INIT(0, "初始状态"),
  /**
   * 自定义HTTP状态-正常
   */
  CONTINUE(100, "请求已被服务器接受,请继续发送剩余部分"),
  /**
   * 自定义HTTP状态-正常
   */
  SWITCHING_PROTOCOLS(101, "请求已被服务器接受,将切换新的协议继续"),
  /**
   * 自定义HTTP状态-正常
   */
  OK(200, "正常"),
  /**
   * 自定义HTTP状态-重定向未找到
   */
  REDIRECT(300, "重定向未找到"),
  /**
   * 自定义HTTP状态-页面未找到
   */
  PAGENOTFOUNT(404, "页面未找到"),
  /**
   * 自定义HTTP状态-HTTP连接异常
   */
  STATUS_CODE_CONNERR(601, "HTTP连接异常"),
  /**
   * 自定义HTTP状态-IO读超时
   */
  STATUS_CODE_TIMEOUT(604, "IO读超时"),
  /**
   * 自定义HTT状态-其它错误
   */
  STATUS_CODE_OTHER(605, "其它错误"),
  /**
   * 未定义异常
   */
  UNDEFINE(999, "未定义异常");

  private Integer code;
  private String message;

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  HttpResultStatus(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
}
