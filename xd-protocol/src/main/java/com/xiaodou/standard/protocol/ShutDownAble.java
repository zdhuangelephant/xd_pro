package com.xiaodou.standard.protocol;

import com.xiaodou.standard.protocol.exception.ShutDownException;

/**
 * @name @see com.xiaodou.standard.protocol.ShutDownAble.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 可关闭性
 * @version 1.0
 */
public interface ShutDownAble {

  /**
   * 关闭
   * 
   * @throws ShutDownException 关闭异常
   */
  public void shutDown() throws ShutDownException;
}
