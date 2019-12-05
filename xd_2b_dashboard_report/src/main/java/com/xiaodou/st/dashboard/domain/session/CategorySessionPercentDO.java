package com.xiaodou.st.dashboard.domain.session;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

/**
 * 
 * @name CategorySessionPercentDO CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月31日
 * @description 专业列表
 * @version 1.0
 */
public class CategorySessionPercentDO {

  public CategorySessionPercentDO getInstance() {
    this.id = null;
    this.alarmCount = null;
    this.catId = null;
    this.catName = null;
    this.chiefUnitId = null;
    this.chiefUnitName = null;

    return this;
  }

  // id
  @Column(isMajor = true)
  @GeneralField
  private Long id;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  /* 专业id */
  private Integer catId;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  /* 第二级单位id */
  private Long chiefUnitId;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  /* 平均学期活跃度 */
  private Double learnPercent = 0d;
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  /* 平均任务完成度 */
  private Double missionPercent = 0d;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  /* 平均学期平均正确率 */
  private Double rightPercent = 0d;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  /* 平均学期平均学习时长 */
  private Long learnTimePercent = 0l;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  /* 预警数量 */
  private Integer alarmCount;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  /* 当期考生人数 */
  private Long studentCount;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  /* 登录管理员所在单位角色类型 */
  private Integer roleType;
  @GeneralField
  @Column(canUpdate = true, sortBy = false)
  /* 登录管理员所在单位id */
  private Integer unitId;
  @GeneralField
  @Column(canUpdate = true, sortBy = true)
  private Timestamp createTime;

  /* 专业名称 */
  private String catName;
  /* 第二级单位名称 */
  private String chiefUnitName;

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public String getChiefUnitName() {
    return chiefUnitName;
  }

  public void setChiefUnitName(String chiefUnitName) {
    this.chiefUnitName = chiefUnitName;
  }

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

  public Long getChiefUnitId() {
    return chiefUnitId;
  }

  public void setChiefUnitId(Long chiefUnitId) {
    this.chiefUnitId = chiefUnitId;
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

  public Double getRightPercent() {
    return rightPercent;
  }

  public void setRightPercent(Double rightPercent) {
    this.rightPercent = rightPercent;
  }

  public Long getLearnTimePercent() {
    return learnTimePercent;
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

  public Long getStudentCount() {
    return studentCount;
  }

  public void setStudentCount(Long studentCount) {
    this.studentCount = studentCount;
  }

  public Integer getRoleType() {
    return roleType;
  }

  public void setRoleType(Integer roleType) {
    this.roleType = roleType;
  }

  public Integer getUnitId() {
    return unitId;
  }

  public void setUnitId(Integer unitId) {
    this.unitId = unitId;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(CategorySessionPercentDO.class,
        "xd_dashboard_category_session_percent",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/session/")
        .buildXml();
  }
}
