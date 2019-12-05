package com.xiaodou.st.dashboard.domain.session;

import java.sql.Timestamp;
import java.util.Date;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

/**
 * 
 * @name CategoryUnitSessionLearnPercentDO CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月31日
 * @description 专业下的，第三级单位下的，趋势分析
 * @version 1.0
 */
public class CategoryUnitSessionLearnPercentDO {
  @Column(isMajor = true)
  @GeneralField
  private Long id;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer catId;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Integer pilotUnitId;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Double learnPercent;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Double missionPercent;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Long learnTimePercent;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  private Double rightPercent;
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Date dateTime;
  private short roleType;
  private Long unitId;
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Timestamp createTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getCatId() {
    return catId;
  }

  public void setCatId(Integer catId) {
    this.catId = catId;
  }

  public Integer getPilotUnitId() {
    return pilotUnitId;
  }

  public void setPilotUnitId(Integer pilotUnitId) {
    this.pilotUnitId = pilotUnitId;
  }

  public Double getRightPercent() {
    return rightPercent;
  }

  public void setRightPercent(Double rightPercent) {
    this.rightPercent = rightPercent;
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

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
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

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(CategoryUnitSessionLearnPercentDO.class,
        "xd_dashboard_category_unit_session_learn_percent",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/session/")
        .buildXml();
  }
}
