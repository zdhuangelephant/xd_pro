package com.xiaodou.resources.model.user;

import java.sql.Timestamp;

public class UserProductOrderModel {
  private Long id;
  private Long userId;
  private Long courseId;
  private Short orderStatus; // 购买状态(1:正常购买状态，2：试用状态)
  private Timestamp orderTime;// 购买时间
  private Timestamp expDate;// 有效期
  private Short isExp;// 是否有效(1:有效2：无效)
  private Short examDateStatus;// 1近期2其它考期(用户自定义)

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public Short getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(Short orderStatus) {
    this.orderStatus = orderStatus;
  }

  public Timestamp getOrderTime() {
    return orderTime;
  }

  public void setOrderTime(Timestamp orderTime) {
    this.orderTime = orderTime;
  }

  public Timestamp getExpDate() {
    return expDate;
  }

  public void setExpDate(Timestamp expDate) {
    this.expDate = expDate;
  }

  public Short getIsExp() {
    return isExp;
  }

  public void setIsExp(Short isExp) {
    this.isExp = isExp;
  }

  public Short getExamDateStatus() {
    return examDateStatus;
  }

  public void setExamDateStatus(Short examDateStatus) {
    this.examDateStatus = examDateStatus;
  }
}
