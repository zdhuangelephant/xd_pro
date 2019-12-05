package com.xiaodou.resources.service.quesbk.rule.impl;

import java.util.List;

import com.xiaodou.resources.model.quesbk.QuesbkExamRule;
import com.xiaodou.resources.service.quesbk.rule.base.AbstractRule;
import com.xiaodou.resources.service.quesbk.rule.base.RandomChooser;
import com.xiaodou.resources.service.quesbk.rule.impl.SimulatePaperRule.SimulatePaperRuleChooser;
import com.xiaodou.resources.service.quesbk.rule.impl.SimulatePaperRule.SimulatePaperRuleEntity;
import com.xiaodou.resources.service.quesbk.rule.iterface.IRule;
import com.xiaodou.resources.service.quesbk.rule.iterface.IRuleChooser;
import com.xiaodou.resources.service.quesbk.rule.model.Rule;
import com.xiaodou.resources.service.quesbk.rule.model.RuleEntity;
import com.xiaodou.resources.service.quesbk.rule.model.RuleEntityParam;

/**
 * @name SimulatePaperRule
 * @CopyRright (c) 2015 by <a hre
 * f="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月9日
 * @description 模拟练习出题规则
 * @version 1.0
 */
public class SimulatePaperRule extends AbstractRule<SimulatePaperRuleEntity>
    implements
      IRule<SimulatePaperRuleChooser, SimulatePaperRuleEntity> {

  /**
   * @name SimulatePaperRuleChooser
   * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月9日
   * @description 模拟练习规则选择器
   * @version 1.0
   */
  static class SimulatePaperRuleChooser extends RandomChooser<SimulatePaperRuleEntity>
      implements
        IRuleChooser<SimulatePaperRuleEntity> {
    private static final SimulatePaperRuleChooser _instance = new SimulatePaperRuleChooser();

    private static SimulatePaperRuleChooser getInstance() {
      return _instance;
    }

    @Override
    public SimulatePaperRuleEntity chooseRule(List<SimulatePaperRuleEntity> ruleList) {
      if (null != ruleList && ruleList.size() == 1) return ruleList.get(0);
      return choose(ruleList);
    }

  }

  /**
   * @name SimulatePaperRuleEntity
   * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月9日
   * @description 模拟练习规则实体
   * @version 1.0
   */
  class SimulatePaperRuleEntity extends RuleEntity {
    public SimulatePaperRuleEntity(QuesbkExamRule examRule) {
      super(examRule);
    }

    @Override
    public List<Rule> initDefaultRule(RuleEntityParam param) {
      throw new RuntimeException("模拟练习没有默认规则");
    }
  }

  @Override
  public SimulatePaperRuleEntity getRuleEntity() {
    return SimulatePaperRuleChooser.getInstance().chooseRule(getRuleEntityList());
  }

  @Override
  public SimulatePaperRuleEntity transfer(QuesbkExamRule examRule) {
    return new SimulatePaperRuleEntity(examRule);
  }
}
