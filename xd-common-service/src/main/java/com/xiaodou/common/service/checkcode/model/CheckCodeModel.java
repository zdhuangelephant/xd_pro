package com.xiaodou.common.service.checkcode.model;

import java.util.Date;

/**
 * 
 * 验证码类
 *
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
public class CheckCodeModel {

  /**
   * 验证码
   */
  private String checkCode;
  
  /**
   * 验证码类型
   */
  private String checkCodeType;
  
  /**
   * 手机号
   */
  private String phoneNum;
  
  /**
   * 设备号
   */
  private String deviceId;
  
  /**
   * 验证码生成时间
   */
  private Date createDate;

  public String getCheckCode() {
    return checkCode;
  }

  public void setCheckCode(String checkCode) {
    this.checkCode = checkCode;
  }

  public String getCheckCodeType() {
    return checkCodeType;
  }

  public void setCheckCodeType(String checkCodeType) {
    this.checkCodeType = checkCodeType;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Override
  public String toString() {
    return "CheckCodeModel [checkCode=" + checkCode + ", checkCodeType=" + checkCodeType
        + ", phoneNum=" + phoneNum + ", deviceId=" + deviceId + ", createDate=" + createDate + "]";
  }
  
}
