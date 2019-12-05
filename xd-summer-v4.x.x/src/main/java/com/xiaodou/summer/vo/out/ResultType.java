package com.xiaodou.summer.vo.out;

import java.net.InetAddress;
import java.net.UnknownHostException;

public enum ResultType {

  /**
   * 成功
   */
  SUCCESS("SUCCESS_CODE", "SUCCESS_MSG"),

  /**
   * 系统异常
   */
  SYSFAIL("SYS_FAIL_CODE", "SYS_FAIL_MSG"),

  /**
   * 参数异常
   */
  VALFAIL("VAL_FAIL_CODE", "VAL_FAIL_MSG"),
  /**
   * 用户未登录
   */
  NOTLOGIN("NOT_LOGIN_CODE", "NOT_LOGIN_MSG");
  
  /**
   * 结果码
   */
  private String code;

  /**
   * 提示信息
   */
  private String msg;

  /**
   * 服务器Ip
   */
  private String serverIp;

  public String getServerIp() {
    return serverIp;
  }

  public String getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

  /**
   * Constract
   * 
   * @param code 结果码
   * @param msg 提示信息
   * @throws UnknownHostException
   */
  ResultType(String code, String msg) {
    this.code = ResultProp.getParams(code);
    this.msg = ResultProp.getParams(msg);
    this.serverIp = "127.0.0.0";
    try {
      InetAddress addr = InetAddress.getLocalHost();
      this.serverIp = addr.getHostAddress().toString();// 获得本机IP
    } catch (UnknownHostException e) {}
  }
}
