package com.xiaodou.standard.protocol;

import com.xiaodou.standard.protocol.exception.ListenException;

/**
 * @name @see com.xiaodou.standard.protocol.ListenAble.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 倾听性
 * @version 1.0
 */
public interface ListenAble {

  /**
   * 倾听消息
   * 
   * @param message 消息
   * @return 倾听结果
   * @throws ListenException 倾听异常
   */
  public <T extends MessageAble> boolean listen(T message) throws ListenException;
}
