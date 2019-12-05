package com.xiaodou.st.dashboard.domain.alarm;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.st.dashboard.constants.enums.CheckStatusEnum;

public class AlarmRecordCheckDO {

  @Column(isMajor = true)
  private Long id;
  /* alarmId 报警记录id */
  @Column(canUpdate = false)
  private String alarmId;
  /* adminId 后台管理员id */
  @Column(canUpdate = false)
  private String adminId;
  /* checkStatus 审核类型 */
  @Column(canUpdate = true)
  private CheckStatusEnum checkStatus;
  /* content 审核内容 */
  @Column(canUpdate = false)
  private String content;
  /* createTime 创建时间 */
  @Column(canUpdate = false)
  private Timestamp createTime;

  public CheckStatusEnum getCheckStatus() {
    return checkStatus;
  }

  public void setCheckStatus(CheckStatusEnum checkStatus) {
    this.checkStatus = checkStatus;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAlarmId() {
    return alarmId;
  }

  public void setAlarmId(String alarmId) {
    this.alarmId = alarmId;
  }

  public String getAdminId() {
    return adminId;
  }

  public void setAdminId(String adminId) {
    this.adminId = adminId;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(AlarmRecordCheckDO.class,
        "xd_dashboard_alarm_record_check",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/alarm/").buildXml();
  }
}
