package com.xiaodou.st.dashboard.domain.apply;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;

@Data
public class ApplyDO {
  /* id 主键id */
  @Column(isMajor = true, listValue = true)
  private Long id;
  /* 第三级单位id */
  private Long pilotUnitId;
  /* 第三级单位名称 */
  private String pilotUnitName;
  /* 所在班级id */
  private Long classId;
  /* 所在班级名称 */
  private String className;
  /* 管理教师id */
  private Integer adminId;
  /* 管理教师姓名 */
  private String adminName;
  /* studentId 学生id */
  @Column(canUpdate = false)
  private Integer studentId;
  private String studentName;
  private String telephone;
  private String regTelephone;
  /* 准考证号 */
  private String admissionCardCode;
  /* majorName 所选专业id */
  @Column(canUpdate = false)
  private Integer catId;
  /* majorName 所选专业 */
  @Column(canUpdate = false)
  private String catName;
  /* courseId 所选课程id */
  @Column(canUpdate = false)
  private Integer productId;
  /* courseId 课程码 */
  @Column(canUpdate = false)
  private String productCode;
  /* courseName 所选课程 */
  @Column(canUpdate = false)
  private String productName;
  /* examDate 开通考期 */
  @Column(canUpdate = false)
  private String examDate;
  /* orderNumber 订单号 */
  @Column(canUpdate = true)
  private Long orderNumber;
  /* 订单状态0：待缴费，1：已缴费，2未缴费 */
  @Column(canUpdate = true, listValue = true)
  private Short orderStatus;
  /* 报名状态（0：后台报名1：业务系统报名成功） */
  @Column(canUpdate = true)
  private Short applyStatus;
  @Column(canUpdate = false)
  private Timestamp createTime;
  @Column(canUpdate = false)
  private Double originalAmount;

  private Long userId;// 执行定时任务时用到
  
  private Integer pageNo;
  private Integer pageSize;

  public void copyApply(ApplyDO ado) {
    if (null != ado.pilotUnitId) this.pilotUnitId = ado.pilotUnitId;
    if (null != ado.pilotUnitName) this.pilotUnitName = ado.pilotUnitName;
    if (null != ado.classId) this.classId = ado.classId;
    if (null != ado.className) this.className = ado.className;
    if (null != ado.adminId) this.adminId = ado.adminId;
    if (null != ado.adminName) this.adminName = ado.adminName;
    if (null != ado.studentId) this.studentId = ado.studentId;
    if (null != ado.studentName) this.studentName = ado.studentName;
    if (null != ado.telephone) this.telephone = ado.telephone;
    if (null != ado.regTelephone) this.regTelephone = ado.regTelephone;
    if (null != ado.admissionCardCode) this.admissionCardCode = ado.admissionCardCode;
    if (null != ado.catId) this.catId = ado.catId;
    if (null != ado.catName) this.catName = ado.catName;
    if (null != ado.productId) this.productId = ado.productId;
    if (null != ado.productCode) this.productCode = ado.productCode;
    if (null != ado.productName) this.productName = ado.productName;
    if (null != ado.examDate) this.examDate = ado.examDate;
    if (null != ado.orderNumber) this.orderNumber = ado.orderNumber;
    if (null != ado.orderStatus) this.orderStatus = ado.orderStatus;
    if (null != ado.applyStatus) this.applyStatus = ado.applyStatus;
    if (null != ado.createTime) this.createTime = ado.createTime;
    if (null != ado.originalAmount) this.originalAmount = ado.originalAmount;
    if (null != ado.userId) this.userId = ado.userId;
  }

}
