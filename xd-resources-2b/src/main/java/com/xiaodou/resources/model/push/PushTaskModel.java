package com.xiaodou.resources.model.push;

import java.sql.Timestamp;

public class PushTaskModel {
//主键id
  private Long id;
  //短信内容
  private String message;
  //短信渠道id
  private Integer channelId;
  //短信模板id
  private Integer templateId;
  //手机号
  private String mobile;
  //创建时间
  private Timestamp createTime;
  //可重试次数
  private Integer canRetryTime;
  //短信模板类型id
  private Integer templateTypeId;
  //状态（0待发送 1 正在发送 ）
  private Integer status;
  //消息类型(0短信 1个推)
  private Integer type;
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public Integer getChannelId() {
    return channelId;
  }
  public void setChannelId(Integer channelId) {
    this.channelId = channelId;
  }
  public Integer getTemplateId() {
    return templateId;
  }
  public void setTemplateId(Integer templateId) {
    this.templateId = templateId;
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
  public Integer getCanRetryTime() {
    return canRetryTime;
  }
  public void setCanRetryTime(Integer canRetryTime) {
    this.canRetryTime = canRetryTime;
  }
  public Integer getTemplateTypeId() {
    return templateTypeId;
  }
  public void setTemplateTypeId(Integer templateTypeId) {
    this.templateTypeId = templateTypeId;
  }
  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }
  public Integer getType() {
    return type;
  }
  public void setType(Integer type) {
    this.type = type;
  }
}
