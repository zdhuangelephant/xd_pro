package com.xiaodou.ms.model.sms;

import java.sql.Timestamp;

/**
 * Created by zyp on 15/6/26.
 */
public class SmsTemplateTypeModel {

  private Integer id;

  private String name;

  private String description;

  private String channelIds;

  private Timestamp createTime;

  private Integer retryTime;

  private Integer cacheTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getRetryTime() {
    return retryTime;
  }

  public void setRetryTime(Integer retryTime) {
    this.retryTime = retryTime;
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

  public Integer getCacheTime() {
    return cacheTime;
  }

  public void setCacheTime(Integer cacheTime) {
    this.cacheTime = cacheTime;
  }
}
