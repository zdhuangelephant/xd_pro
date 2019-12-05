package com.xiaodou.server.mapi.domain;

public class OfficialInfo {
  private String realName;
  private String gender;
  private String phoneNum;
  private String admissionCardCode;
  private String identificationCardCode;
  private String majorCode;
  private String majorName;
  private String courseCode;
  private String courseName;
  private String merchant;


  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getIdentificationCardCode() {
    return identificationCardCode;
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

  public String getMajorCode() {
    return majorCode;
  }

  public void setMajorCode(String majorCode) {
    this.majorCode = majorCode;
  }

  public String getMajorName() {
    return majorName;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName;
  }

  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }
}
