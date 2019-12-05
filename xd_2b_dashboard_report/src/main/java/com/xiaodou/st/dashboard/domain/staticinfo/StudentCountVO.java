package com.xiaodou.st.dashboard.domain.staticinfo;

import lombok.Data;

@Data
public class StudentCountVO {
  /* 第三级单位id */
  private Long pilotUnitId;
  /* 第三级单位名称 */
  private String pilotUnitName;
  /*学生总人数*/
  private Integer studentCount = 0;
  /*报名学生人数*/
  private Integer payStudentCount = 0;
  /*已缴费报名信息*/
  private Integer payApplyCount = 0;
}
