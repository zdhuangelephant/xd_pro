package com.xiaodou.st.dashboard.domain.dashboard;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * 
 * @name LastWeekMissionPercentDO CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月31日
 * @description 工作台-上周第三级单位榜
 * @version 1.0
 */
public class LastWeekMissionPercentDO {
  @Column(isMajor = true)
  private Long id;
  /**
   * 排名
   */
  private Integer rank;
  /**
   * 趋势（对应自己的排名） 1上升 0持平 -1下降
   */
  private Long tendency;
  /**
   * 第三级单位id
   */
  private Long pilotUnitId;
  /**
   * 任务完成度
   */
  private Double missionPercent=0d;
  /**
   * 角色排名类型
   */
  private short roleType;
  /**
   * 单位id
   */
  private Long unitId;
  private Timestamp createTime;

  /* 第三级单位名称 */
  private String pilotUnitName;
  /* 第三级单位头像 */
  private String pilotUnitPortrait;

  public String getPilotUnitName() {
    return pilotUnitName;
  }

  public void setPilotUnitName(String pilotUnitName) {
    this.pilotUnitName = pilotUnitName;
  }

  public String getPilotUnitPortrait() {
    return pilotUnitPortrait;
  }

  public void setPilotUnitPortrait(String pilotUnitPortrait) {
    this.pilotUnitPortrait = pilotUnitPortrait;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getRank() {
    return rank;
  }

  public void setRank(Integer rank) {
    this.rank = rank;
  }

  public Long getTendency() {
    return tendency;
  }

  public void setTendency(Long tendency) {
    this.tendency = tendency;
  }

  public Long getPilotUnitId() {
    return pilotUnitId;
  }

  public void setPilotUnitId(Long pilotUnitId) {
    this.pilotUnitId = pilotUnitId;
  }

  public double getMissionPercent() {
    return missionPercent;
  }

  public void setMissionPercent(double missionPercent) {
    this.missionPercent = missionPercent;
  }

  public short getRoleType() {
    return roleType;
  }

  public void setRoleType(short roleType) {
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
    MybatisXmlTool.getInstance(LastWeekMissionPercentDO.class,
        "xd_dashboard_last_week_mission_percent",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/dashboard/")
        .buildXml();
  }
}
