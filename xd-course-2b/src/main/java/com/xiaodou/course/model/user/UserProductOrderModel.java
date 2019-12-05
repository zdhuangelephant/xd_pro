package com.xiaodou.course.model.user;

import java.sql.Timestamp;

/**
 * @name UserProductOrderModel 
 * CopyRright (c) 2018 by 李德洪
 *
 * @author 李德洪
 * @date 2018年2月24日
 * @description 废弃类，业务中已经不再使用
 * @version 1.0
 * @deprecated
 */
public class UserProductOrderModel {
  private Long id;
  private Long userId;
  private Long courseId;
  private Short orderStatus; // 购买状态(1:正常购买状态，2：试用状态)
  private Timestamp orderTime;// 购买时间
  private Timestamp expDate;// 有效期
  private Short isExp;// 是否有效(1:有效2：无效)
  // private String myExamDate;// 我的考试期（1610）每条数据有且最多只存在两个选择项（**04/**10）
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
