package com.xiaodou.ucenter.model;

import java.sql.Timestamp;

public class AppBindingModel {
  // 主键id
  private String id;
  /** module 所属模块 */
  private String module;
  // 设备号
  private String deviceId;
  // 个推id
  private String pushId;
  // 系统类型
  private String systemType;
  // 别名（用户手机号）
  private String userId;
  // 创建时间
  private Timestamp createTime;
  // 登录状态
  private String status;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getPushId() {
    return pushId;
  }

  public void setPushId(String pushId) {
    this.pushId = pushId;
  }

  public String getSystemType() {
    return systemType;
  }

  public void setSystemType(String systemType) {
    this.systemType = systemType;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public enum AppBindingStatus {
    LOGIN("0"), OUTLOGIN("1");
    private String code;

    AppBindingStatus(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

  }

  @Override
  public String toString() {
    return "AppBindingModel [id=" + id + ", deviceId=" + deviceId + ", module=" + module
        + ", pushId=" + pushId + ", systemType=" + systemType + ", userId=" + userId
        + ", createTime=" + createTime + "]";
  }


}
