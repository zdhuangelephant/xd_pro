package com.xiaodou.resources.model.user;

import java.sql.Timestamp;

/**
 * Created by zyp on 15/8/9.
 */
public class UserLearnRecordModel {

  // id
  private Integer id;

  // 用户Id
  private Integer userId;

  // 产品Id
  private Integer productId;

  // 章节Id
  private Integer chapterId;

  // 条目Id
  private Integer itemId;

  // 记录时间
  private Timestamp recordTime;

  // 学习时长
  private Integer learnTime;

  // appId
  private Integer moduleId;

  private Short learnType;// 类型（pk(11做题，12解析),闯关（21做题，22解析），31修炼，41错题）

 // private Short learnScope;// 范围（courseId,chapterId,itemId的非空组合范围）


  public Short getLearnType() {
    return learnType;
  }

  public void setLearnType(Short learnType) {
    this.learnType = learnType;
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

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public Integer getChapterId() {
    return chapterId;
  }

  public void setChapterId(Integer chapterId) {
    this.chapterId = chapterId;
  }

  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }
}
