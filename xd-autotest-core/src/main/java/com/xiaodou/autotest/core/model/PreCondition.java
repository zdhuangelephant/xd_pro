package com.xiaodou.autotest.core.model;

import org.springframework.util.Assert;

import com.xiaodou.autotest.core.ActionConstant;
import com.xiaodou.autotest.core.ActionEnum.ConditionScope;
import com.xiaodou.autotest.core.ActionTool;
import com.xiaodou.autotest.core.interfaces.Condition;
import com.xiaodou.autotest.core.vo.SandBoxContext;

/**
 * @name @see com.xiaodou.autotest.core.model.PreCondition.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年2月9日
 * @description 前置条件模型
 * @version 1.0
 */
public class PreCondition implements Condition {

  /** key 键 */
  private String key;
  /** condition 条件 */
  private ConditionScope condition = ActionConstant.DEFAULT_CONDITION;
  /** targetValue 目标值 */
  private String targetValue;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public ConditionScope getCondition() {
    return condition;
  }

  public void setCondition(ConditionScope condition) {
    Assert.notNull(condition, "PreCondition's conditionScope can't be null.");
    this.condition = condition;
  }

  public void setCondition(String condition) {
    ConditionScope conditionScope = ActionTool.getEnumValue(ConditionScope.class, condition);
    setCondition(conditionScope);
  }

  public String getTargetValue() {
    return targetValue;
  }

  public void setTargetValue(String targetValue) {
    this.targetValue = targetValue;
  }

  @Override
  public Boolean validate(SandBoxContext sandBox) {
    return condition.check(sandBox.getParam(key), targetValue);
  }

}
