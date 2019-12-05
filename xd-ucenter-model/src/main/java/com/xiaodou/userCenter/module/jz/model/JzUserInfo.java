package com.xiaodou.userCenter.module.jz.model;
import com.xiaodou.userCenter.model.BaseUserInfo;

public class JzUserInfo extends BaseUserInfo {
  
  /**
   * 报考类型
   */
  private Integer type;
  /**
   * 学历
   */
  private Integer degree;
  /**
   * 考期
   */
  private String examDate;
  /**
   * 报考城市
   */
  private Integer city;
  public Integer getType() {
    return type;
  }
  public void setType(Integer type) {
    this.type = type;
  }
  public Integer getDegree() {
    return degree;
  }
  public void setDegree(Integer degree) {
    this.degree = degree;
  }
  public String getExamDate() {
    return examDate;
  }
  public void setExamDate(String examDate) {
    this.examDate = examDate;
  }
  public Integer getCity() {
    return city;
  }
  public void setCity(Integer city) {
    this.city = city;
  }

}
