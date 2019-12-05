package com.xiaodou.st.dashboard.domain.session;
public class CategorySessionPercentVO {
  
  private Long id ;
  private Integer catId;
  /* 平均学期活跃度 */
  private String learnPercent = "0";
  /* 平均任务完成度 */
  private String missionPercent = "0";
  /* 平均学期平均正确率 */
  private String rightPercent = "0";
  /* 平均学期平均学习时长 */
  private Long learnTimePercent = 0l;
  /* 预警数量 */
  private Integer alarmCount=0;
  /* 当期考生人数 */
  private Long studentCount=0l;
  /* 专业名称 */
  private String catName;
  /* 第二级单位名称 */
  private String chiefUnitName;

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

  public String getRightPercent() {
    return rightPercent;
  }

  public void setRightPercent(String rightPercent) {
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
}
