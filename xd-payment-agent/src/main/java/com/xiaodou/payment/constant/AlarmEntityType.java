package com.xiaodou.payment.constant;


/**
 * 支付消息长时间未返回(超时关单)
 * 支付请求未能够成功发送（response null)
 * 支付平台 返回的状态码不正确
 */
public enum AlarmEntityType {

  HTTP_SERVICE_UNVALID("[通信服务不可用]"),
  PAYMENT_RETURN_ERROR("[payment-返回码异常]"),

  RED_BONUSES_HTTP_ERROR("[redBonuses通信服务不可用]"),
  RED_BONUSES_RETURN_ERROR("[redBonuses返回异常]");

  private String serviceName = "ELONG-OMS";
  private String moduleName;

  AlarmEntityType(String moduleName) {
    this.moduleName = moduleName;
  }

//  public BaseAlarmEntityType getType() {
//    return new BaseAlarmEntityType(serviceName, moduleName);
//  }

}

