package com.xiaodou.sms.model;

import java.sql.Timestamp;

public class SmsTemplateModel {
  //主键id
  private Integer id;
  //短信模板内容
  private  String messageContent;
  //短信模板描述
  private String description;
  //模板类型id
  private Integer typeId;
  //创建时间
  private Timestamp createTime;
  //状态（模板是否生效，0=停用,1-启用）
  private Integer status;
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getMessageContent() {
    return messageContent;
  }
  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Integer getTypeId() {
    return typeId;
  }
  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }
  public Timestamp getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }
  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }
  @Override
  public String toString() {
    return "SmsTemplateModel [id=" + id + ", messageContent=" + messageContent + ", description="
        + description + ", typeId=" + typeId + ", createTime=" + createTime + ", status=" + status
        + "]";
  }
}
