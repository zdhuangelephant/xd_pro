package com.xiaodou.supernetwork.context;

import java.util.UUID;

import com.xiaodou.standard.protocol.UniqueAble;

/**
 * @name @see com.xiaodou.supernetwork.context.SimpleContext.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月23日
 * @description 基础上下文
 * @version 1.0
 */
public class SimpleContext implements UniqueAble {

  /** uniqueId 唯一上下文ID */
  private String uniqueId = UUID.randomUUID().toString();

  @Override
  public String uniqueId() {
    return uniqueId;
  }
}
