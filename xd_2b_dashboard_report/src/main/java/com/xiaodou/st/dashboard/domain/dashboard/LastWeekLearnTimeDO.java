package com.xiaodou.st.dashboard.domain.dashboard;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * 
 * @name LastWeekLearnTimeDO CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月30日
 * @description 工作台，上周学霸榜
 * @version 1.0
 */
public class LastWeekLearnTimeDO {
  @Column(isMajor = true)
  private Long id;
  /**
   * 排名
   */
  private Integer rank;
  /**
   * 学生id
   */
  private Long studentId;
  /**
   * 学习时长
   */
  private Long learnTime;
  /**
   * 答题数
   */
  private Long answerCount;
  /**
   * 正确率
   */
  private Double rightPercent;
  /**
   * 角色排名类型
   */
  private short roleType;
  /**
   * 单位id
   */
  private Long unitId;
  private Timestamp createTime;

  private String realName;
  private String pilotUnitName;
  private String className;
  private String portrait;

  public String getPortrait() {
    return portrait;
  }

  public void setPortrait(String portrait) {
    this.portrait = portrait;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getPilotUnitName() {
    return pilotUnitName;
  }

  public void setPilotUnitName(String pilotUnitName) {
    this.pilotUnitName = pilotUnitName;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
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

  public Long getStudentId() {
    return studentId;
  }

  public void setStudentId(Long studentId) {
    this.studentId = studentId;
  }

  public Long getLearnTime() {
    return learnTime;
    // return StDateUtil.getCeilInt((double) (learnTime/60));
  }

  public void setLearnTime(Long learnTime) {
    this.learnTime = learnTime;
  }

  public Long getAnswerCount() {
    return answerCount;
  }

  public void setAnswerCount(Long answerCount) {
    this.answerCount = answerCount;
  }

  public Double getRightPercent() {
    return rightPercent;
  }

  public void setRightPercent(Double rightPercent) {
    this.rightPercent = rightPercent;
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
    MybatisXmlTool.getInstance(LastWeekLearnTimeDO.class, "xd_dashboard_last_week_learn_time",
        "D:/work/workspace_xd/xd_2b_dashboard_report/src/main/resources/conf/mybatis/dashboard/")
        .buildXml();
  }
}
