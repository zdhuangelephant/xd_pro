package com.xiaodou.st.dashboard.domain.student;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

import com.google.common.collect.Lists;
import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;

@Data
public class StudentDO {
  /* id 主键id */
  @Column(isMajor = true)
  private Integer id;
  /* 第三级单位id */
  private Long pilotUnitId;
  /* 第三级单位名称 */
  private String pilotUnitName;
  /* 主考单位集 */
  private String chiefUnits;
  /* 专业集 */
  private String cats;
  /* classId 所在班级id */
  @Column(canUpdate = true)
  private Long classId;
  /* 班级名称 */
  private String className;
  @Column(canUpdate = false)
  private Integer adminId;
  /* 管理教师姓名 */
  private String adminName;
  /* realName 姓名 */
  @Column(canUpdate = true)
  private String realName;
  /* explain 性别 */
  @Column(canUpdate = true)
  private String gender;
  /* tellPhone 手机号 */
  @Column(canUpdate = true)
  private String telephone;
  private String regTelephone;
  /* 业务用户id */
  private Long userId;
  /* identificationCardCode 身份证号 */
  @Column(canUpdate = true)
  private String identificationCardCode;
  /* admissionCardCode 准考证号 */
  @Column(canUpdate = true)
  private String admissionCardCode = StringUtils.EMPTY;
  /* 考生头像 */
  private String portrait;
  /* 人脸识别源头像 */
  private String sourcePortrait;
  /* 人脸采集方式 */
  private String collectWay;
  /* 人脸上传时间 */
  private Timestamp uploadTime;
  /* 人脸上传设备 */
  private String uploadDevice;
  /* 学生状态 0、未注册，1、注册成功，2、注册失败，已经存在该学生3、注册异常，4、成功导入*/
  @Column(canUpdate = true)
  private Short studentStatus;
  @Column(canUpdate = false)
  private Timestamp createTime;

  /* 展示 */
  private List<ApplyDO> applyList = Lists.newArrayList();
  private Short deleteStatus;

  private Integer pageNo;
  private Integer pageSize;
  
  /** 分组查询学生数返回字段*/
  private Integer studentCount;
  
  public static void main(String[] args) {
    MybatisXmlTool.getInstance(StudentDO.class, "xd_dashboard_student",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/student/")
        .buildXml();
  }

}
