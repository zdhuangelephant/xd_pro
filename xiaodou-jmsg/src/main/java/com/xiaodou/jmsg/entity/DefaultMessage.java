package com.xiaodou.jmsg.entity;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * 默认消息对象
 */
public class DefaultMessage {
  // 不能改变字段名称，否则老版本json反序列化会异常
  private UUID messageID;
  private UUID contextID;
  private String messageName;
  private String failedCount;
  private int processCount;
  private int deadLetterCount;
  private Date sendTime;
  private Date receiveTime;
  private String sendFromClass;
  private String transferObjectTypeName;
  private String transferObjectJSON;
  private String customTag;
  private String sendServerIP;
  private String sendServerName;
  private boolean saved;
  private String routeKey;
  private Map<String, Boolean> consumerExcutedResultDic;
  private String queueName;
/*  private String traceId;
  private String myId;*/
  public UUID getMessageID() {
    return messageID;
  }

  public void setMessageID(UUID messageID) {
    this.messageID = messageID;
  }

  public UUID getContextID() {
    return contextID;
  }

  public void setContextID(UUID contextID) {
    this.contextID = contextID;
  }

  public String getMessageName() {
    return messageName;
  }

  public void setMessageName(String messageName) {
    this.messageName = messageName;
  }

  public String getFailedCount() {
    return failedCount;
  }

  public void setFailedCount(String failedCount) {
    this.failedCount = failedCount;
  }

  public int getProcessCount() {
    return processCount;
  }

  public void setProcessCount(int processCount) {
    this.processCount = processCount;
  }

  public int getDeadLetterCount() {
    return deadLetterCount;
  }

  public void setDeadLetterCount(int deadLetterCount) {
    this.deadLetterCount = deadLetterCount;
  }

  public Date getSendTime() {
    return sendTime;
  }

  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }

  public String getSendFromClass() {
    return sendFromClass;
  }

  public void setSendFromClass(String sendFromClass) {
    this.sendFromClass = sendFromClass;
  }

  public String getTransferObjectTypeName() {
    return transferObjectTypeName;
  }

  public void setTransferObjectTypeName(String transferObjectTypeName) {
    this.transferObjectTypeName = transferObjectTypeName;
  }

  public String getTransferObjectJSON() {
    return transferObjectJSON;
  }

  public void setTransferObjectJSON(String transferObjectJSON) {
    this.transferObjectJSON = transferObjectJSON;
  }

  public String getCustomTag() {
    return customTag;
  }

  public void setCustomTag(String customTag) {
    this.customTag = customTag;
  }

  public String getSendServerIP() {
    return sendServerIP;
  }

  public void setSendServerIP(String sendServerIP) {
    this.sendServerIP = sendServerIP;
  }

  public String getSendServerName() {
    return sendServerName;
  }

  public void setSendServerName(String sendServerName) {
    this.sendServerName = sendServerName;
  }

  public boolean isSaved() {
    return saved;
  }

  public void setSaved(boolean saved) {
    this.saved = saved;
  }

  public String getRouteKey() {
    return routeKey;
  }

  public void setRouteKey(String routeKey) {
    this.routeKey = routeKey;
  }

  public Map<String, Boolean> getConsumerExcutedResultDic() {
    return consumerExcutedResultDic;
  }

  public void setConsumerExcutedResultDic(Map<String, Boolean> consumerExcutedResultDic) {
    this.consumerExcutedResultDic = consumerExcutedResultDic;
  }

  public Date getReceiveTime() {
    return receiveTime;
  }

  public void setReceiveTime(Date receiveTime) {
    this.receiveTime = receiveTime;
  }

  public String getQueueName() {
    return queueName;
  }

  public void setQueueName(String queueName) {
    this.queueName = queueName;
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

}
