package com.xiaodou.st.dashboard.domain.grade;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
@Data
public class ClassDO {
  /* id 主键id */
  @Column(isMajor = true)
  private Long id;
  /* 第三级单位id*/
  private Long pilotUnitId;
  /* 第三级单位名称*/
  private String pilotUnitName;
  /*管理教师id*/
  private Integer adminId;
  /*管理教师姓名*/
  private String adminName;
  /* className 班级名称 */
  @Column(canUpdate = true)
  private String className;
  /* explain 说明 */
  @Column(canUpdate = true)
  private String description;
  /* studentCount 班级人数 */
  private Long studentCount;
  @Column(canUpdate = false)
  private Timestamp createTime;
  private Short updateStatus;
  /*0、不可以批量刪除1、可以批量刪除 */
  private Short batchDel;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(ClassDO.class, "xd_dashboard_class",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/grade/").buildXml();
  }
}
