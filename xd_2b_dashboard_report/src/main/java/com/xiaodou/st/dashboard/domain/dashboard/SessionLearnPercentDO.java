package com.xiaodou.st.dashboard.domain.dashboard;

import java.sql.Timestamp;
import java.util.Date;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

/**
 * 
 * @name SessionLearnPercentModel CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月30日
 * @description 工作台，趋势分析
 * @version 1.0
 */
public class SessionLearnPercentDO {
  @Column(isMajor = true)
  private Integer id;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private double learnPercent;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private double missionPercent;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Long learnTimePercent;
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Date dateTime;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private short roleType;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Long unitId;
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Timestamp createTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public double getLearnPercent() {
    return learnPercent;
  }

  public void setLearnPercent(double learnPercent) {
    this.learnPercent = learnPercent;
  }

  public double getMissionPercent() {
    return missionPercent;
  }

  public void setMissionPercent(double missionPercent) {
    this.missionPercent = missionPercent;
  }

  public Long getLearnTimePercent() {
    return learnTimePercent;
    // return StDateUtil.getCeilInt((double) (learnTimePercent/60));
  }

  public void setLearnTimePercent(Long learnTimePercent) {
    this.learnTimePercent = learnTimePercent;
  }

  public Date getDateTime() {
    return dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
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
    MybatisXmlTool.getInstance(SessionLearnPercentDO.class, "xd_dashboard_session_learn_percent",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/dashboard/")
        .buildXml();
  }
}
