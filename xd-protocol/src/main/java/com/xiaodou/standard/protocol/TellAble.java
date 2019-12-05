package com.xiaodou.standard.protocol;

import com.xiaodou.standard.protocol.exception.TellException;

/**
 * @name @see com.xiaodou.standard.protocol.TellAble.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 倾诉性
 * @version 1.0
 */
public interface TellAble {

  /**
   * 倾诉消息
   * 
   * @param message 消息
   * @return 倾诉结果
   * @throws TellException 倾诉异常
   */
  public <T extends MessageAble> boolean tell(T message) throws TellException;
}
