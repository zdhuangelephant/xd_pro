package com.xiaodou.oms.util.log;


public enum AlarmEntityType {

  HTTP_SERVICE_UNVALID("[通信服务不可用]"), PAYMENT_REFUND_ERROR("[payment-退款失败]"), MQMESSAGE_ERR(
      "[rabbitmq发送失败]"), ASYNCMESSAGE_LOST_ERROR("[异步消息漏发补发]"), MESSAGE_CONSUME_ERR("[oms-消息消费异常]");

  private String serviceName = "OMS";
  private String moduleName;

  AlarmEntityType(String moduleName) {
    this.moduleName = moduleName;
  }

}
