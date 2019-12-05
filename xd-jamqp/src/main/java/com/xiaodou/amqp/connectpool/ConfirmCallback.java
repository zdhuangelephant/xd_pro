package com.xiaodou.amqp.connectpool;

public interface ConfirmCallback {
  /**
   * 服务器返回
   */
  static final int SERVER_RESPONSE = 0;
  static final int NOT_RECEIVED = 1;

  /**
   * 
   * @param msg message data for the callback
   * @param ack ack true for ack, false for nack
   * @param causeType 0 is server response, 1 is not receive ack when timeout or connection closed
   */
  void confirm(String msg, boolean ack, int causeType);

}
