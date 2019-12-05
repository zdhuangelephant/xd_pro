package com.xiaodou.message.service.platform;

import com.xiaodou.message.model.MessageModel;
import com.xiaodou.message.vo.MessagePushInfo;

public interface IMsPlatformService {
  /**
   * 发送推送消息接口
   * @param messageInput
   * @return
   */
  public MessageModel sendMs(MessagePushInfo messageInput);
}
