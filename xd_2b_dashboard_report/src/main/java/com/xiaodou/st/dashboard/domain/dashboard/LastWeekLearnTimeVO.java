package com.xiaodou.st.dashboard.domain.dashboard;

public class LastWeekLearnTimeVO {
  /**
   * 排名
   */
  private Integer rank;
  private String portrait;
  private String realName;
  private String pilotUnitName;
  private String className;
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
  private String rightPercent;
  public Integer getRank() {
    return rank;
  }
  public void setRank(Integer rank) {
    this.rank = rank;
  }
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
  public Long getLearnTime() {
    return learnTime;
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
  public String getRightPercent() {
    return rightPercent;
  }
  public void setRightPercent(String rightPercent) {
    this.rightPercent = rightPercent;
  }
}
