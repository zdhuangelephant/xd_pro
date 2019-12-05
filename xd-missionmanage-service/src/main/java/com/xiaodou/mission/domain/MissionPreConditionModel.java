package com.xiaodou.mission.domain;

import com.xiaodou.mission.engine.model.MissionPreConditionInstance;

/**
 * @name @see com.xiaodou.mission.domain.MissionPreConditionModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 任务前置条件模型
 * @version 1.0
 */
public class MissionPreConditionModel {

  public MissionPreConditionModel() {}

  public MissionPreConditionModel(MissionPreConditionInstance instance) {
    if (null != instance.getPreCond()) {
      this.preCond = instance.getPreCond().toString();
    }
    this.threshold = instance.getThreshold();
  }

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