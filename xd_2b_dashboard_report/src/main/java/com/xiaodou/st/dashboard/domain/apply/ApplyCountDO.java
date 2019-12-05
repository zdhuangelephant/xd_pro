package com.xiaodou.st.dashboard.domain.apply;

import lombok.Data;

@Data
public class ApplyCountDO {
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
  /* majorName 所选专业id */
  private Integer catId;
  /* majorName 所选专业 */
  private String catName;
  /* courseId 所选课程id */
  private Integer productId;
  /* courseId 课程码 */
  private String productCode;
  /* courseName 所选课程 */
  private String productName;
  /* examDate 开通考期 */
  private String examDate;
  /* orderNumber 订单号 */
  private Long orderNumber;
  /* 订单状态0：待缴费，1：已缴费，2未缴费 */
  private Short orderStatus;
  /* 报名状态（0：后台报名1：业务系统报名成功） */
  private Short applyStatus;

  /** studentNum 学生数量 */
  private Integer studentNum;
  /** applyCount 报名科次数量 */
  private Integer applyCount;

}
