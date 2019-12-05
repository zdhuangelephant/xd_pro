package com.xiaodou.course.model.user;

import java.sql.Timestamp;

/**
 * Created by zyp on 15/8/9.
 */
public class UserLearnProcessModel {

  private Integer id;

  // 用户Id
  private Integer userId;

  // 产品Id
  private Integer productId;

  // 条目Id
  private Integer itemId;

  // 记录时间
  private Timestamp recordTime;

  // appId
  private Integer moduleId;

  // 章节Id
  private Integer chapterId;

  public Integer getChapterId() {
    return chapterId;
  }

  public void setChapterId(Integer chapterId) {
    this.chapterId = chapterId;
  }

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

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }

  public Timestamp getRecordTime() {
    return recordTime;
  }

  public void setRecordTime(Timestamp recordTime) {
    this.recordTime = recordTime;
  }

  public Integer getModuleId() {
    return moduleId;
  }

  public void setModuleId(Integer moduleId) {
    this.moduleId = moduleId;
  }
}
