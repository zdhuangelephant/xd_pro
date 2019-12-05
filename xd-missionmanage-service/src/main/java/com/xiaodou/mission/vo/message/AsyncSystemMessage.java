package com.xiaodou.mission.vo.message;

import com.xiaodou.async.model.SystemMessage;

/**
 * @name AsyncSystemMessage
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月24日
 * @description 异步发送系统消息参数类
 * @version 1.0
 */
public class AsyncSystemMessage extends SystemMessage {
  /** ASYNC_SYSTEM_MESSAGE 系统消息异步消息名 */
  private final static String ASYNC_SYSTEM_MESSAGE = "systemmessage";

  public AsyncSystemMessage(String module) {
    setMessageName(ASYNC_SYSTEM_MESSAGE);
  }
}
