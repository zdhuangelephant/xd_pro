package com.xiaodou.st.dashboard.domain.dashboard;


public class LastWeekMissionPercentVO {
  /**
   * 排名
   */
  private Integer rank;
  /**
   * 趋势（对应自己的排名） 1上升 0持平 -1下降
   */
  private Long tendency;
  /**
   * 任务完成度
   */
  private String missionPercent="0";
  /* 第三级单位名称 */
  private String pilotUnitName;
  /* 第三级单位头像 */
  private String pilotUnitPortrait;
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
  public String getMissionPercent() {
    return missionPercent;
  }
  public void setMissionPercent(String missionPercent) {
    this.missionPercent = missionPercent;
  }
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
}
