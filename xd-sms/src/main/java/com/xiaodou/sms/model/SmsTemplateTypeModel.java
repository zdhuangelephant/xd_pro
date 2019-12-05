package com.xiaodou.sms.model;

import java.sql.Timestamp;

public class SmsTemplateTypeModel {
  //主键id
  private Integer id;
  //任务名称
  private String name;
  //任务描述
  private String description;
  //渠道ids
  private String channelIds;
  //创建时间
  private Timestamp createTime;
  //
  private  Integer retryTime;
  private Integer cacheTime;
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getChannelIds() {
    return channelIds;
  }
  public void setChannelIds(String channelIds) {
    this.channelIds = channelIds;
  }
  public Timestamp getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }
  public Integer getRetryTime() {
    return retryTime;
  }
  public void setRetryTime(Integer retryTime) {
    this.retryTime = retryTime;
  }
  public Integer getCacheTime() {
    return cacheTime;
  }
  public void setCacheTime(Integer cacheTime) {
    this.cacheTime = cacheTime;
  }
  @Override
  public String toString() {
    return "SmsTemplateTypeModel [id=" + id + ", name=" + name + ", description=" + description
        + ", channelIds=" + channelIds + ", createTime=" + createTime + ", retryTime=" + retryTime
        + ", cacheTime=" + cacheTime + "]";
  }
  
}
