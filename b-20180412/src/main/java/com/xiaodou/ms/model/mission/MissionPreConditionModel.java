package com.xiaodou.ms.model.mission;

public class MissionPreConditionModel {

  public MissionPreConditionModel() {}
  /** preCond 前置条件 */
  private String preCond;
  /** threshold 阀值 */
  private String threshold;
  /** nextCond  */
  private String nextCond;

  public String getPreCond() {
    return preCond;
  }

  public void setPreCond(String preCond) {
    this.preCond = preCond;
  }

  public String getThreshold() {
    return threshold;
  }

  public void setThreshold(String threshold) {
    this.threshold = threshold;
  }

  public String getNextCond() {
    return nextCond;
  }

  public void setNextCond(String nextCond) {
    this.nextCond = nextCond;
  }
  
}