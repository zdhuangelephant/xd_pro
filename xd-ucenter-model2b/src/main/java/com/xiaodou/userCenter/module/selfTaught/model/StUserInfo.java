package com.xiaodou.userCenter.module.selfTaught.model;

import com.xiaodou.userCenter.model.BaseUserInfo;

public class StUserInfo extends BaseUserInfo {

  private String major;// 用户已选专业的专业码值
  private String sign; // 签名
  private String medal; // 称号 FastJsonUtil.toJson(StMedal);
  private String picList;// 图片

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
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

    public String getMedalId() {
      return medalId;
    }

    public void setMedalId(String medalId) {
      this.medalId = medalId;
    }

    public String getMedalName() {
      return medalName;
    }

    public void setMedalName(String medalName) {
      this.medalName = medalName;
    }

    public String getMedalImg() {
      return medalImg;
    }

    public void setMedalImg(String medalImg) {
      this.medalImg = medalImg;
    }
  }
  
  public static class Picture {
    
    private String pictrue;

    public String getPictrue() {
      return pictrue;
    }

    public void setPictrue(String pictrue) {
      this.pictrue = pictrue;
    }
    
  }

}
