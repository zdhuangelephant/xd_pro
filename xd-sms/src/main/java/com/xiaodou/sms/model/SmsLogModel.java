package com.xiaodou.sms.model;

import java.sql.Timestamp;


/**
 * 日志javaBean
 * 
 * @author wuyunkuo
 *
 */
public class SmsLogModel {
  // 主键id
  private int id;
  // 短信内容
  private String message;
  // 发送状态（0 发送失败；1 发送成功）
  private int sendStatus;
  // 短信通道id
  private int channelId;
  // 短信模板id
  private int templateId;
  // 短信通道返回的结果
  private String channelSendResult;
  // 手机号
  private String mobile;
  // 创作时间
  private Timestamp createTime;
  // 操作时间
  private Timestamp operateTime;
  // 模板id
  private int typeId;

  /** appMessageId 应用消息ID */
  private String appMessageId; 
  /** productLine 产品线 */
  private String productLine;


  public Timestamp getOperateTime() {
    return operateTime;
  }

  public void setOperateTime(Timestamp operateTime) {
    this.operateTime = operateTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getSendStatus() {
    return sendStatus;
  }

  public void setSendStatus(int sendStatus) {
    this.sendStatus = sendStatus;
  }

  public int getChannelId() {
    return channelId;
  }

  public void setChannelId(int channelId) {
    this.channelId = channelId;
  }

  public int getTemplateId() {
    return templateId;
  }

  public void setTemplateId(int templateId) {
    this.templateId = templateId;
  }

  public String getChannelSendResult() {
    return channelSendResult;
  }

  public void setChannelSendResult(String channelSendResult) {
    this.channelSendResult = channelSendResult;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public int getTypeId() {
    return typeId;
  }

  public void setTypeId(int typeId) {
    this.typeId = typeId;
  }

  public String getAppMessageId() {
    return appMessageId;
  }

  public void setAppMessageId(String appMessageId) {
    this.appMessageId = appMessageId;
  }

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

 

}
