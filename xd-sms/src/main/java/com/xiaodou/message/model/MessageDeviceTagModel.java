package com.xiaodou.message.model;

import java.sql.Timestamp;

public class MessageDeviceTagModel {
  //标识id
  private String tagId;
  //设备号
  private Integer deviceId;//=(Jpush)registrationId
  //创建时间
  private Timestamp createTime;
  public String getTagId() {
    return tagId;
  }
  public void setTagId(String tagId) {
    this.tagId = tagId;
  }
  public Integer getDeviceId() {
    return deviceId;
  }
  public void setDeviceId(Integer deviceId) {
    this.deviceId = deviceId;
  }
  public Timestamp getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }
  @Override
  public String toString() {
    return "MessageDeviceTagModel [tagId=" + tagId + ", deviceId=" + deviceId + ", createTime="
        + createTime + "]";
  }
}
