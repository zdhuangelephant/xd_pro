package com.xiaodou.dashboard.model.log;

import java.util.Date;

import com.xiaodou.jmsg.entity.DefaultMessage;
import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;

/**
 * @name @see com.xiaodou.logmonitor.domain.JmsgMessageModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月3日
 * @description 异步消息数据模型
 * @version 1.0
 */
@CollectionName("jmsg_model")
public class JmsgMessageModel  {
  private String id;
  @Pk
  private String customTag;
  private String messageName;
  private DefaultMessage messageBody;
  // 0 unknown 1 success
  private Integer status;
  private Date updateTime = new Date();
  private Long nextSendTime;
  private Integer retryTimes;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCustomTag() {
    return customTag;
  }

  public void setCustomTag(String customTag) {
    this.customTag = customTag;
  }

  public String getMessageName() {
    return messageName;
  }

  public void setMessageName(String messageName) {
    this.messageName = messageName;
  }

  public DefaultMessage getMessageBody() {
    return messageBody;
  }

  public void setMessageBody(DefaultMessage messageBody) {
    this.messageBody = messageBody;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Long getNextSendTime() {
    return nextSendTime;
  }

  public void setNextSendTime(Long nextSendTime) {
    this.nextSendTime = nextSendTime;
  }

  public Integer getRetryTimes() {
    return retryTimes;
  }

  public void setRetryTimes(Integer retryTimes) {
    this.retryTimes = retryTimes;
  }

}
