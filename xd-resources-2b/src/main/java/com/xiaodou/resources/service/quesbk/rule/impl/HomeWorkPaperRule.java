package com.xiaodou.resources.service.quesbk.rule.impl;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.model.quesbk.QuesbkExamRule;
import com.xiaodou.resources.service.quesbk.rule.base.AbstractRule;
import com.xiaodou.resources.service.quesbk.rule.base.RandomChooser;
import com.xiaodou.resources.service.quesbk.rule.impl.HomeWorkPaperRule.HomeWorkPaperRuleChooser;
import com.xiaodou.resources.service.quesbk.rule.impl.HomeWorkPaperRule.HomeWorkPaperRuleEntity;
import com.xiaodou.resources.service.quesbk.rule.iterface.IRule;
import com.xiaodou.resources.service.quesbk.rule.iterface.IRuleChooser;
import com.xiaodou.resources.service.quesbk.rule.model.Rule;
import com.xiaodou.resources.service.quesbk.rule.model.RuleEntity;
import com.xiaodou.resources.service.quesbk.rule.model.RuleEntityParam;
import com.xiaodou.resources.util.QuesbkUtil;
import com.xiaodou.resources.util.QuesbkUtil.QuesNum;
import com.xiaodou.resources.util.StaticInfoProp;

/**
 * @name HomeWorkPaperRule
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月9日
 * @description 课后练习出题规则
 * @version 1.0
 */
public class HomeWorkPaperRule extends AbstractRule<HomeWorkPaperRuleEntity>
    implements
      IRule<HomeWorkPaperRuleChooser, HomeWorkPaperRuleEntity> {

  /**
   * @name HomeWorkPaperRuleChooser
   * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月9日
   * @description 课后练习规则选择器
   * @version 1.0
   */
  static class HomeWorkPaperRuleChooser extends RandomChooser<HomeWorkPaperRuleEntity>
      implements
        IRuleChooser<HomeWorkPaperRuleEntity> {
    private static final HomeWorkPaperRuleChooser _instance = new HomeWorkPaperRuleChooser();

    private static HomeWorkPaperRuleChooser getInstance() {
      return _instance;
    }

    @Override
    public HomeWorkPaperRuleEntity chooseRule(List<HomeWorkPaperRuleEntity> ruleList) {
      if (null != ruleList && ruleList.size() == 1) return ruleList.get(0);
      return choose(ruleList);
    }

  }

  /**
   * @name HomeWorkPaperRuleEntity
   * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月9日
   * @description 课后练习规则实体
   * @version 1.0
   */
  class HomeWorkPaperRuleEntity extends RuleEntity {
    public HomeWorkPaperRuleEntity(QuesbkExamRule examRule) {
      super(examRule);
    }

    @Override
    public List<Rule> initDefaultRule(RuleEntityParam param) {
      List<Rule> ruleLst = Lists.newArrayList();
      QuesNum qusNum = QuesbkUtil.getQuesNum(QuesBaseConstant.HOMEWORK_DEFAULT_QUESNUM);
      Rule rule = new Rule();
      rule.setProductChapterId(Integer.parseInt(param.getItemId()));
      rule.setQuestionType(StaticInfoProp.getInt(QuesBaseConstant.QUES_TYPE_SIMPLE_CHOICE));
      rule.setQuestionNum(qusNum.getQuesNum());
      rule.setUserId(param.getUid());
      ruleLst.add(rule);
      return ruleLst;
    }
  }

  @Override
  public HomeWorkPaperRuleEntity getRuleEntity() {
    return HomeWorkPaperRuleChooser.getInstance().chooseRule(getRuleEntityList());
  }

  @Override
  public HomeWorkPaperRuleEntity transfer(QuesbkExamRule examRule) {
    return new HomeWorkPaperRuleEntity(examRule);
  }
}
