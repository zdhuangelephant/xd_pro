package com.xiaodou.oms.vo.result;

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
   * 订单不存在异常
   */
  ORDERMIS("ORDER_MIS_CODE", "ORDER_MIS_MSG"),

  /**
   * 重复下单
   */
  ORDERREPEAT("ORDER_REPEAT_CODE", "ORDER_REPEAT_MSG"),

  /**
   * 第三方接口调用异常
   */
  MERCHANTERROR("MERCHANT_ERR_CODE", "MERCHANT_ERR_MSG"),

  /**
   * 找不到支付信息
   */
  UNKNOWNPAYMENT("UNKNOWN_PAYMENT_CODE", "UNKNOWN_PAYMENT_MSG"),

  /**
   * 黑名单用户
   */
  BLACKLIST("BLACK_LIST_CODE", "BLACK_LIST_MSG"),

  /**
   * 获取交易Token失败
   */
  MISTOKEN("MIS_TOKEN_CODE", "MIS_TOKEN_MSG"),

  /**
   * 状态机事件异常
   */
  STATEMACHINEERR("STATEMACHINE_ERR_CODE", "STATEMACHINE_ERR_MSG"),
  
  /**
   * 前置检查失败异常
   */
  PRECHECKFAIL("PRECHECK_FAIL_CODE", "PRECHECK_FAIL_MSG"),
  
  /**
   * 未注册Transition异常
   */
  UNKNOWNTRANSITION("UNKNOWN_TRANSITION_CODE", "UNKNOWN_TRANSITION_MSG");
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
