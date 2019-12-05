package com.xiaodou.jmsg.entity;

import java.util.UUID;

import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.jmsg.entity.AbstractMessagePojo.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 消息封装模型
 * @version 1.0
 */
public class AbstractMessagePojo {
	
  public AbstractMessagePojo(){}
  public AbstractMessagePojo(String messageName) {
    this.messageName = messageName;
  }

  private Object transferObject;
  private String customTag;
  private String messageName;

  public final Object getTransferObject() {
    return transferObject;
  }

  public final void setTransferObject(Object transferObject) {
    this.transferObject = transferObject;
  }

  public final String getCustomTag() {
    if (StringUtils.isBlank(this.customTag)) synchronized (this) {
      if (StringUtils.isBlank(this.customTag)) this.customTag = UUID.randomUUID().toString();
    }
    return customTag;
  }


  public final void setCustomTag(String customTag) {
    if (StringUtils.isBlank(this.customTag)) synchronized (this) {
      if (StringUtils.isBlank(this.customTag)) this.customTag = customTag;
    }
  }


  public final String getMessageName() {
    return messageName;
  }
  public void setMessageName(String messageName) {
	this.messageName = messageName;
  }

}
