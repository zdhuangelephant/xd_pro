package com.xiaodou.userCenter.module.selfTaught.model;


/**
 * 
 * @name StOfficialInfo CopyRright (c) 2016 by 51xiaodou.com
 * 
 * @author lidehong
 * @date 2016年5月5日
 * @description 官方导入用户数据模型
 * @version 1.0
 */
public class StOfficialInfo extends StUserInfo {
  private String realName;// 真实姓名
  private String officialGender;// 官方导入性别
  private String identificationCardCode;// 身份证号
  private String admissionCardCode; // 准考证号
  private String majorName; // 专业名称
  private String degreeLevel; // 学习层次
  private String merchant; // 学习机构

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getIdentificationCardCode() {
    return identificationCardCode;
  }

  public String getOfficialGender() {
    return officialGender;
  }

  public void setOfficialGender(String officialGender) {
    this.officialGender = officialGender;
  }

  public void setIdentificationCardCode(String identificationCardCode) {
    this.identificationCardCode = identificationCardCode;
  }

  public String getAdmissionCardCode() {
    return admissionCardCode;
  }

  public void setAdmissionCardCode(String admissionCardCode) {
    this.admissionCardCode = admissionCardCode;
  }

  public String getMajorName() {
    return majorName;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName;
  }

  public String getDegreeLevel() {
    return degreeLevel;
  }

  public void setDegreeLevel(String degreeLevel) {
    this.degreeLevel = degreeLevel;
  }

  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }
}
