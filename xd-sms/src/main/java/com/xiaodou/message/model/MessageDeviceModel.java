package com.xiaodou.message.model;

import java.sql.Timestamp;

public class MessageDeviceModel {
  //主键id
  private Long id;
  //设备token
  private String deviceToken;
  //设备号
  private String deviceId;
  //用户id
  private String userId;
  //手机号
  private String telephone;
  //创建时间
  private Timestamp createTime;
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getDeviceToken() {
    return deviceToken;
  }
  public void setDeviceToken(String deviceToken) {
    this.deviceToken = deviceToken;
  }
  public String getDeviceId() {
    return deviceId;
  }
  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getTelephone() {
    return telephone;
  }
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }
  public Timestamp getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }
  @Override
  public String toString() {
    return "MessageDeviceModel [id=" + id + ", deviceToken=" + deviceToken + ", deviceId="
        + deviceId + ", userId=" + userId + ", telephone=" + telephone + ", createTime="
        + createTime + "]";
  }
}
