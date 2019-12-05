package com.xiaodou.jmsg.server.model;

import java.sql.Timestamp;
import java.util.Date;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;
/**
 * @name @see com.xiaodou.jmsg.server.model.MessageBody.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 消息发送体
 * @version 1.0
 */
public class MessageBody {
@GeneralField
@Column(canUpdate = true, sortBy = false,isMajor = true)
  private String messageId;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private String contextId;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private String customTag;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private String messageName;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private String fromClass;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private String messageDataType;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private String messageData;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private int messageSize;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private Timestamp messageSendTime;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private Timestamp messageReceiveTime;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private String sendServerName;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private String sendServerIp;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private String queueName;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private String routeKey;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private String processServerName;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private String processServerIp;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private String processStatus;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private String result;
@GeneralField
@Column(canUpdate = true, sortBy = false)
  private String failedCount;
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
@Column(canUpdate = true, sortBy = false)
  private Timestamp createTime;

@GeneralField
@Column(canUpdate = true, sortBy = false)
  private int updateUserId;
  
   private String surfix;

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

  public String getFromClass() {
    return fromClass;
  }

  public void setFromClass(String fromClass) {
    this.fromClass = fromClass;
  }

  public String getMessageDataType() {
    return messageDataType;
  }

  public void setMessageDataType(String messageDataType) {
    this.messageDataType = messageDataType;
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

  public void setMessageSendTime(Timestamp messageSendTime) {
    this.messageSendTime = messageSendTime;
  }

  public Date getMessageReceiveTime() {
    return messageReceiveTime;
  }

  public void setMessageReceiveTime(Timestamp messageReceiveTime) {
    this.messageReceiveTime = messageReceiveTime;
  }

  public String getSendServerName() {
    return sendServerName;
  }

  public void setSendServerName(String sendServerName) {
    this.sendServerName = sendServerName;
  }

  public String getSendServerIp() {
    return sendServerIp;
  }

  public void setSendServerIp(String sendServerIp) {
    this.sendServerIp = sendServerIp;
  }

  public String getQueueName() {
    return queueName;
  }

  public void setQueueName(String queueName) {
    this.queueName = queueName;
  }

  public String getRouteKey() {
    return routeKey;
  }

  public void setRouteKey(String routeKey) {
    this.routeKey = routeKey;
  }

  public String getProcessServerName() {
    return processServerName;
  }

  public void setProcessServerName(String processServerName) {
    this.processServerName = processServerName;
  }

  public String getProcessServerIp() {
    return processServerIp;
  }

  public void setProcessServerIp(String processServerIp) {
    this.processServerIp = processServerIp;
  }


  public String getFailedCount() {
    return failedCount;
  }

  public void setFailedCount(String failedCount) {
    this.failedCount = failedCount;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public Date getBeginProcessTime() {
    return beginProcessTime;
  }

  public void setBeginProcessTime(Timestamp beginProcessTime) {
    this.beginProcessTime = beginProcessTime;
  }

  public Date getEndProcessTime() {
    return endProcessTime;
  }

  public void setEndProcessTime(Timestamp endProcessTime) {
    this.endProcessTime = endProcessTime;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public int getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(int updateUserId) {
    this.updateUserId = updateUserId;
  }

  public String getProcessStatus() {
    return processStatus;
  }

  public void setProcessStatus(String processStatus) {
    this.processStatus = processStatus;
  }
	public static void main(String[] args) {
	    MybatisXmlTool.getInstance(MessageBody.class, "message_body${input.surfix}",
	            "E:/work1/xiaodou-jmsg-server/src/main/resources/conf/mybatis/")
	            .buildXml();
	}

	public String getSurfix() {
		return surfix;
	}

	public void setSurfix(String surfix) {
		this.surfix = surfix;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
