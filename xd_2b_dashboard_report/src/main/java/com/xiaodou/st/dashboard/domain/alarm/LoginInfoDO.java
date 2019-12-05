package com.xiaodou.st.dashboard.domain.alarm;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

@Data
public class LoginInfoDO {
  /* id 主键id */
  @Column(isMajor = true, betweenScope = true)
  private Long id;
  /* 学生id */
  @Column(canUpdate = false)
  private Integer studentId;
  /* systemType 系统类型 */
  @Column(canUpdate = false)
  private String systemType;
  /* deviceId 设备号 */
  @Column(canUpdate = false)
  private String deviceId;
  /* loginTime 登录时间 */
  @Column(canUpdate = false)
  private Timestamp loginTime;
  private String area;
  /* createTime 创建时间 */
  @Column(canUpdate = false)
  private Timestamp createTime;

  /* 业务系统id */
  private Long businessId;

  private String studentName;
  private String admissionCardCode;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(LoginInfoDO.class, "xd_dashboard_login_info",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/alarm/")
        .buildXml();
  }
}
