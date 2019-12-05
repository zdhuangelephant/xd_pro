package com.xiaodou.st.dashboard.domain.session;


public class CategoryUnitSessionPercentVO {
  /* 专业id */
  private Long catId;
  /* 第三级单位id */
  private Long pilotUnitId;
  /* 第三级单位名称 */
  private String pilotUnitName;
  /* 当期考生人数 */
  private Long studentCount = 0l;
  /* 学期平均正确率 */
  private String rightPercent = "0";
  /* 平均学期活跃度 */
  private String learnPercent = "0";
  /* 平均任务完成度 */
  private String missionPercent = "0";
  /* 学期平均学习时长 */
  private Long learnTimePercent = 0l;
  /* 预警数量 */
  private Integer alarmCount = 0;

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

  public String getPilotUnitName() {
    return pilotUnitName;
  }

  public void setPilotUnitName(String pilotUnitName) {
    this.pilotUnitName = pilotUnitName;
  }

  public Long getStudentCount() {
    return studentCount;
  }

  public void setStudentCount(Long studentCount) {
    this.studentCount = studentCount;
  }

  public String getRightPercent() {
    return rightPercent;
  }

  public void setRightPercent(String rightPercent) {
    this.rightPercent = rightPercent;
  }

  public String getLearnPercent() {
    return learnPercent;
  }

  public void setLearnPercent(String learnPercent) {
    this.learnPercent = learnPercent;
  }

  public String getMissionPercent() {
    return missionPercent;
  }

  public void setMissionPercent(String missionPercent) {
    this.missionPercent = missionPercent;
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
}
