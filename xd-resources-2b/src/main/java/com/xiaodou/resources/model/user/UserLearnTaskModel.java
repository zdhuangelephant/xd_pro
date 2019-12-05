package com.xiaodou.resources.model.user;

import java.sql.Timestamp;

import com.xiaodou.common.annotation.BaseField;

/**
 * Created by zyp on 15/8/23.
 */
public class UserLearnTaskModel {

  // 主键
  @BaseField
  private Integer id;

  // 产品Id
  @BaseField
  private Integer productId;

  // itemId
  @BaseField
  private Integer itemId;

  // 任务比例
  @BaseField
  private Integer taskRatio;

  // 完成时间
  @BaseField
  private Timestamp completeTime;

  // 开始时间
  @BaseField
  private Timestamp beginTime;

  // 模块Id
  @BaseField
  private Integer moduleId;

  // 用户Id
  @BaseField
  private Integer userId;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public Integer getTaskRatio() {
    return taskRatio;
  }

  public void setTaskRatio(Integer taskRatio) {
    this.taskRatio = taskRatio;
  }

  public Timestamp getCompleteTime() {
    return completeTime;
  }

  public void setCompleteTime(Timestamp completeTime) {
    this.completeTime = completeTime;
  }

  public Timestamp getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Timestamp beginTime) {
    this.beginTime = beginTime;
  }

  public Integer getModuleId() {
    return moduleId;
  }

  public void setModuleId(Integer moduleId) {
    this.moduleId = moduleId;
  }
}
