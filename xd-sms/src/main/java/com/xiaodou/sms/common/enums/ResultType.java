package com.xiaodou.sms.common.enums;

import java.net.InetAddress;
import java.net.UnknownHostException;

public enum ResultType {

	  /**
	   * 成功有数据
	   */
	  SUCCESS(0, "成功"),
	  /**
	   * 系统异常
	   */
	  SYSFAIL(-1, "小逗系统异常"),

	  /**
	   * 参数校验错误
	   */
	  VALFAIL(-2, "参数校验错误"),
	  /**
       * 参数校验错误
       */
      NOTLOGIN(-3, "用户未登陆");

	  /**
	   * 结果码
	   */
	  private Integer code;

	  /**
	   * 提示信息
	   */
	  private String msg;

	  /**
	   * 服务器Ip
	   */
	  private String serverIp;

	  public Integer getCode() {
	    return code;
	  }

	  public String getMsg() {
	    return msg;
	  }

	  public String getServerIp() {
	    return serverIp;
	  }

	  ResultType(Integer code, String msg) {
	    this.code = code;
	    this.msg = msg;
	    this.serverIp = "127.0.0.0";
	    try {
	      InetAddress addr = InetAddress.getLocalHost();
	      this.serverIp = addr.getHostAddress().toString();// 获得本机IP
	    } catch (UnknownHostException e) {
	    }
	  }
}
