package com.xiaodou.mission.engine.model;

import java.util.UUID;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.domain.MissionPreConditionModel;
import com.xiaodou.mission.engine.MissionEnums;
import com.xiaodou.mission.engine.MissionEnums.MissionPreCondition;

/**
 * @name @see com.xiaodou.mission.engine.model.MissionPreConditionInstance.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 任务前置条件实体
 * @version 1.0
 */
public class MissionPreConditionInstance {

  /** 默认构造类型 */
  public MissionPreConditionInstance() {}

  /**
   * 带参构造类型
   * 
   * @param model 任务前置条件模型
   */
  public MissionPreConditionInstance(MissionPreConditionModel model) {
    if (StringUtils.isNotBlank(model.getPreCond())) {
      this.preCond = MissionEnums.getEnumValue(MissionPreCondition.class, model.getPreCond());
    }
    this.threshold = model.getThreshold();
    if (StringUtils.isNotBlank(model.getNextCond())) {
      this.nextCond = FastJsonUtil.fromJson(model.getNextCond(), MissionPreConditionInstance.class);
    }
  }

  private String id = UUID.randomUUID().toString();
  /** preCond 前置条件 */
  private MissionPreCondition preCond;
  /** threshold 阀值 */
  private String threshold;
  /** nextCond 下一个条件 */
  private MissionPreConditionInstance nextCond;

  public MissionPreCondition getPreCond() {
    return preCond;
  }

  public void setPreCond(MissionPreCondition preCond) {
    this.preCond = preCond;
  }

  public String getThreshold() {
    return threshold;
  }

  public void setThreshold(String threshold) {
    this.threshold = threshold;
  }

  public MissionPreConditionInstance getNextCond() {
    return nextCond;
  }

  public void setNextCond(MissionPreConditionInstance nextCond) {
    if (null == nextCond || checkMobius(nextCond)) {
      throw new RuntimeException("插入了循环前置条件");
    }
    if (null == this.nextCond) {
      this.nextCond = nextCond;
    } else {
      this.nextCond.setNextCond(nextCond);
    }
  }

  public boolean match(Context context) {
    if (null == preCond || preCond.compare(context, threshold)) {
      if (null != this.nextCond) {
        return this.nextCond.match(context);
      }
      return true;
    }
    return false;
  }

  private boolean checkMobius(MissionPreConditionInstance nextCond) {
    if (nextCond.id.equals(this.id)) {
      return true;
    }
    if (null != this.nextCond) {
      return this.nextCond.checkMobius(nextCond);
    }
    return false;
  }

}
