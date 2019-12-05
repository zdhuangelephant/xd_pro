package com.xiaodou.standard.protocol;

import com.xiaodou.standard.protocol.exception.StartUpFailException;

/**
 * @name @see com.xiaodou.standard.protocol.StartUpAble.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2018年2月10日
 * @description 可启动性
 * @version 1.0
 */
public interface StartUpAble {

  /**
   * 启动
   * 
   * @throws StartUpFailException 启动异常
   */
  public void startUp() throws StartUpFailException;
}
