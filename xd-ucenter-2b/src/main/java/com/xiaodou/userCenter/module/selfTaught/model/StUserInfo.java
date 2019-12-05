package com.xiaodou.userCenter.module.selfTaught.model;

import com.xiaodou.userCenter.model.BaseUserInfo;

import lombok.Data;

public class StUserInfo extends BaseUserInfo {

  private String major;// 用户已选专业的专业码值
  private String sign; // 签名
  private String medal; // 称号 FastJsonUtil.toJson(StMedal);
  private String picList;// 图片
  /**module 地区*/
  private String region;
  /**moduleName 地区名称*/
  private String regionName;
  /**majorId 专业id*/
  private String majorId;
  /**majorName 专业名称*/
  private String majorName;

  @Data
  public static class StMedal {
    /**
     * 勋章id
     */
    private String medalId;
    /**
     * 勋章名称
     */
    private String medalName;
    /**
     * 勋章图片
     */
    private String medalImg;
  }
  
  @Data
  public static class Picture {
    private String pictrue;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getMedal() {
    return medal;
  }

  public void setMedal(String medal) {
    this.medal = medal;
  }

  public String getPicList() {
    return picList;
  }

  public void setPicList(String picList) {
    this.picList = picList;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  public String getMajorId() {
    return majorId;
  }

  public void setMajorId(String majorId) {
    this.majorId = majorId;
  }

  public String getMajorName() {
    return majorName;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName;
  }

}
