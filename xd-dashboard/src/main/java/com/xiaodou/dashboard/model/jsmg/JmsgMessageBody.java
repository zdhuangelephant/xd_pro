package com.xiaodou.dashboard.model.jsmg;

import java.sql.Timestamp;
import java.util.Date;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

public class JmsgMessageBody {
  @GeneralField
  @Column(isMajor = true, canUpdate = true, sortBy = false)
  private String messageId;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String contextId;
  @GeneralField
  @Column(canUpdate = true, sortBy = false, likeValue = true)
  private String customTag;
  @GeneralField
  @Column(canUpdate = true, sortBy = false, likeValue = true)
  private String messageName;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String messageData;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private int messageSize;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Date messageSendTime;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Date messageReceiveTime;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String sendServerName;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private String queueName;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private int failedCount;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private int priority;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Timestamp beginProcessTime;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Timestamp endProcessTime;
  @GeneralField
  @Column(sortBy = true)
  private Timestamp createTime;
  @GeneralField
  @Column(sortBy = false)
  private String result;

  public String getMessageId() {
    return messageId;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public String getContextId() {
    return contextId;
  }

  public void setContextId(String contextId) {
    this.contextId = contextId;
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


  public String getMessageData() {
    return messageData;
  }

  public void setMessageData(String messageData) {
    this.messageData = messageData;
  }

  public int getMessageSize() {
    return messageSize;
  }

  public void setMessageSize(int messageSize) {
    this.messageSize = messageSize;
  }

  public Date getMessageSendTime() {
    return messageSendTime;
  }

  public void setMessageSendTime(Date messageSendTime) {
    this.messageSendTime = messageSendTime;
  }

  public Date getMessageReceiveTime() {
    return messageReceiveTime;
  }

  public void setMessageReceiveTime(Date messageReceiveTime) {
    this.messageReceiveTime = messageReceiveTime;
  }

  public String getSendServerName() {
    return sendServerName;
  }

  public void setSendServerName(String sendServerName) {
    this.sendServerName = sendServerName;
  }


  public String getQueueName() {
    return queueName;
  }

  public void setQueueName(String queueName) {
    this.queueName = queueName;
  }

  public int getFailedCount() {
    return failedCount;
  }

  public void setFailedCount(int failedCount) {
    this.failedCount = failedCount;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }


  public static void main(String[] args) {
    MybatisXmlTool.getInstance(JmsgMessageBody.class, "message_body${input.surfix}",
        "src/main/resources/conf/mybatis/jmsg/").buildXml();
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getEndProcessTime() {
    return endProcessTime;
  }

  public void setEndProcessTime(Timestamp endProcessTime) {
    this.endProcessTime = endProcessTime;
  }

  public Timestamp getBeginProcessTime() {
    return beginProcessTime;
  }

  public void setBeginProcessTime(Timestamp beginProcessTime) {
    this.beginProcessTime = beginProcessTime;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}
