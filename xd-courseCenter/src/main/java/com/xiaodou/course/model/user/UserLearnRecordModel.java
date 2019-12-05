package com.xiaodou.course.model.user;

import java.sql.Timestamp;

/**
 * Created by zyp on 15/8/9.
 */
public class UserLearnRecordModel {

  // id
  private Integer id;

  // 用户Id
  private Integer userId;

  // 记录时间
  private Timestamp recordTime;

  // 学习时
  private Integer learnTime;

  // appId
  private Integer moduleId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Timestamp getRecordTime() {
    return recordTime;
  }

  public void setRecordTime(Timestamp recordTime) {
    this.recordTime = recordTime;
  }

  public Integer getLearnTime() {
    return learnTime;
  }

  public void setLearnTime(Integer learnTime) {
    this.learnTime = learnTime;
  }

  public Integer getModuleId() {
    return moduleId;
  }

  public void setModuleId(Integer moduleId) {
    this.moduleId = moduleId;
  }
}
