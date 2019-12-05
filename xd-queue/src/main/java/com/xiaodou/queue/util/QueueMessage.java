package com.xiaodou.queue.util;

import com.xiaodou.common.util.log.model.MessageEntity;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.enums.CallBackStatus;
import com.xiaodou.queue.message.DefaultMessage;

public class QueueMessage extends MessageEntity<DefaultMessage> {

  public QueueMessage(CallBackStatus status, DefaultMessage pojo) {
    super(pojo, pojo.getMessageId().toString(), pojo.getMessageName());
    setResult(status.toString());
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

}
