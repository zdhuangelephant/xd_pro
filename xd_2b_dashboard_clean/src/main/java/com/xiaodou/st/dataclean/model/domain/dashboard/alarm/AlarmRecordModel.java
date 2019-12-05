package com.xiaodou.st.dataclean.model.domain.dashboard.alarm;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.st.dataclean.model.transport.BaseTransferModel;

/**
 * @name @see com.xiaodou.st.dataclean.model.domain.dashboard.alarm.AlarmRecordModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月6日
 * @description 报警记录模型
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlarmRecordModel extends BaseTransferModel {

  /** id 主键ID */
  @Column(isMajor = true, autoIncrement = true)
  private Integer id;
  /** studentId 学生ID */
  private Integer studentId;
  /** deviceId 设备ID */
  private String deviceId;
  /** triggerId 触发报警记录ID */
  private String triggerId;
  /** triggerType 触发报警类型 */
  private Short triggerType;
  /** alarmLevel 报警级别 */
  private String alarmLevel;
  /** alarmType 报警类型 */
  private String alarmType;
  /** status 报警状态 */
  private String status;
  /** pretreatment 预处理类型 */
  private String pretreatment;
  /** alarmTime 报警时间 */
  @Column(betweenScope = true)
  private Timestamp alarmTime;
  /** roleType 角色类型 */
  private Short roleType;
  /** unitId 单位ID */
  private Integer unitId;
  /** readStatus 读取状态 */
  private Short readStatus;
  /** createTime 创建时间 */
  @Column(betweenScope = true)
  private Timestamp createTime;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(AlarmRecordModel.class, "xd_dashboard_alarm_record",
        "src/main/resources/conf/mybatis/dashboard/").buildXml();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getStudentId() {
    return studentId;
  }

  public void setStudentId(Integer studentId) {
    this.studentId = studentId;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getTriggerId() {
    return triggerId;
  }

  public void setTriggerId(String triggerId) {
    this.triggerId = triggerId;
  }

  public Short getTriggerType() {
    return triggerType;
  }

  public void setTriggerType(Short triggerType) {
    this.triggerType = triggerType;
  }

  public String getAlarmLevel() {
    return alarmLevel;
  }

  public void setAlarmLevel(String alarmLevel) {
    this.alarmLevel = alarmLevel;
  }

  public String getAlarmType() {
    return alarmType;
  }

  public void setAlarmType(String alarmType) {
    this.alarmType = alarmType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPretreatment() {
    return pretreatment;
  }

  public void setPretreatment(String pretreatment) {
    this.pretreatment = pretreatment;
  }

  public Timestamp getAlarmTime() {
    return alarmTime;
  }

  public void setAlarmTime(Timestamp alarmTime) {
    this.alarmTime = alarmTime;
  }

  public Short getRoleType() {
    return roleType;
  }

  public void setRoleType(Short roleType) {
    this.roleType = roleType;
  }

  public Integer getUnitId() {
    return unitId;
  }

  public void setUnitId(Integer unitId) {
    this.unitId = unitId;
  }

  public Short getReadStatus() {
    return readStatus;
  }

  public void setReadStatus(Short readStatus) {
    this.readStatus = readStatus;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

}
