package com.xiaodou.jmsg.client;

import com.xiaodou.amqp.connectpool.ConfirmCallback;
import com.xiaodou.common.util.log.LoggerUtil;

public class MessageConfirmCallback implements ConfirmCallback {
  
  @Override
  public void confirm(String msg, boolean ack, int causeType) {
    if(ack) {
      LoggerUtil.debug("Ack Type: " + causeType + ". msg: " + msg);
    } else {
      LoggerUtil.debug("Nack Type: " + causeType + ". msg: " + msg);
      PersistentMessageQueue.getInstance().putQueue(msg);
    }    
  }
}
