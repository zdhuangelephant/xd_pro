package com.xiaodou.userCenter.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AppBindingModel {
  // 主键id
  private String id;
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
    return "AppBindingModel [id=" + id + ", deviceId=" + deviceId
        + ", pushId=" + pushId + ", systemType=" + systemType + ", userId=" + userId
        + ", createTime=" + createTime + "]";
  }
}
