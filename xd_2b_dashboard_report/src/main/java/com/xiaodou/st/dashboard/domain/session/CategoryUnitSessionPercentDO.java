package com.xiaodou.st.dashboard.domain.session;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * 
 * @name CategoryUnitSessionPercentDO CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月31日
 * @description 专业下的第三级单位列表
 * @version 1.0
 */
public class CategoryUnitSessionPercentDO {
  @Column(isMajor = true)
  private Long id;
  /* 专业id */
  private Long catId;
  /* 第三级单位id */
  private Long pilotUnitId;
  /* 当期考生人数 */
  private Long studentCount;
  /* 学期平均正确率 */
  private Double rightPercent = 0d;
  /* 平均学期活跃度 */
  private Double learnPercent = 0d;
  /* 平均任务完成度 */
  private Double missionPercent = 0d;
  /* 学期平均学习时长 */
  private Long learnTimePercent = 0l;
  /* 预警数量 */
  private Integer alarmCount = 0;
  private Short roleType;
  private Long unitId;
  private Timestamp createTime;

  /* 第三级单位名称 */
  private String pilotUnitName;

  public String getPilotUnitName() {
    return pilotUnitName;
  }

  public void setPilotUnitName(String pilotUnitName) {
    this.pilotUnitName = pilotUnitName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCatId() {
    return catId;
  }

  public void setCatId(Long catId) {
    this.catId = catId;
  }

  public Long getPilotUnitId() {
    return pilotUnitId;
  }

  public void setPilotUnitId(Long pilotUnitId) {
    this.pilotUnitId = pilotUnitId;
  }

  public Long getStudentCount() {
    return studentCount;
  }

  public void setStudentCount(Long studentCount) {
    this.studentCount = studentCount;
  }

  public Double getRightPercent() {
    return rightPercent;
  }

  public void setRightPercent(Double rightPercent) {
    this.rightPercent = rightPercent;
  }

  public Double getLearnPercent() {
    return learnPercent;
  }

  public void setLearnPercent(Double learnPercent) {
    this.learnPercent = learnPercent;
  }

  public Double getMissionPercent() {
    return missionPercent;
  }

  public void setMissionPercent(Double missionPercent) {
    this.missionPercent = missionPercent;
  }

  public Long getLearnTimePercent() {
    return learnTimePercent;
    // return StDateUtil.getCeilInt((double) (learnTimePercent / 60));
  }

  public void setLearnTimePercent(Long learnTimePercent) {
    this.learnTimePercent = learnTimePercent;
  }

  public Integer getAlarmCount() {
    return alarmCount;
  }

  public void setAlarmCount(Integer alarmCount) {
    this.alarmCount = alarmCount;
  }

  public Short getRoleType() {
    return roleType;
  }

  public void setRoleType(Short roleType) {
    this.roleType = roleType;
  }

  public Long getUnitId() {
    return unitId;
  }

  public void setUnitId(Long unitId) {
    this.unitId = unitId;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(CategoryUnitSessionPercentDO.class,
        "xd_dashboard_category_unit_session_percent",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/session/")
        .buildXml();
  }
}
