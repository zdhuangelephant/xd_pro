package com.xiaodou.engine.rule.impl;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.domain.product.QuesbkExamRule;
import com.xiaodou.domain.product.QuesbkQues;
import com.xiaodou.engine.rule.base.AbstractRule;
import com.xiaodou.engine.rule.base.RandomChooser;
import com.xiaodou.engine.rule.impl.RealPaperRule.RealPageRuleEntity;
import com.xiaodou.engine.rule.impl.RealPaperRule.RealPaperRuleChooser;
import com.xiaodou.engine.rule.iterface.IRule;
import com.xiaodou.engine.rule.iterface.IRuleChooser;
import com.xiaodou.engine.rule.model.Rule;
import com.xiaodou.engine.rule.model.RuleEntity;
import com.xiaodou.engine.rule.model.RuleEntityParam;

/**
 * @name RealPaperRule
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月8日
 * @description 真题练习出题规则
 * @version 1.0
 */
public class RealPaperRule extends AbstractRule<RealPageRuleEntity>
    implements
      IRule<RealPaperRuleChooser, RealPageRuleEntity> {

  /**
   * @name RealPaperRuleChooser
   * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月8日
   * @description 真题练习规则选择器
   * @version 1.0
   */
  static class RealPaperRuleChooser extends RandomChooser<RealPageRuleEntity>
      implements
        IRuleChooser<RealPageRuleEntity> {
    private static final RealPaperRuleChooser _instance = new RealPaperRuleChooser();

    private static RealPaperRuleChooser getInstance() {
      return _instance;
    }

    @Override
    public RealPageRuleEntity chooseRule(List<RealPageRuleEntity> ruleList) {
      if (null != ruleList && ruleList.size() == 1) return ruleList.get(0);
      return choose(ruleList);
    }
  }

  /**
   * @name RealPageRuleEntity
   * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月8日
   * @description 真题练习规则实体
   * @version 1.0
   */
  class RealPageRuleEntity extends RuleEntity {
    public RealPageRuleEntity(QuesbkExamRule examRule) {
      super(examRule);
    }

    @Override
    protected void _fetchRule(RuleEntityParam param, Rule rule) {
      return;
    }

    @Override
    public List<Rule> initDefaultRule(RuleEntityParam param) {
      return null;
    }

    @Override
    protected void processRuleList(RuleEntityParam param, List<QuesbkQues> quesLst,
        List<Rule> ruleList, String uid) throws InterruptedException {
      processRule(null, param, quesLst, uid);
    }

    @Override
    protected void processRule(Rule rule, RuleEntityParam param, List<QuesbkQues> quesLst,
        String uid) {
      List<String> quesIdLst =
          FastJsonUtil.fromJsons(getPaper().getQuesIds(), new TypeReference<List<String>>() {});
      if (null == quesIdLst || quesIdLst.size() == 0) return;
      quesLst.addAll(param.getService().queryQuesList(quesIdLst, uid, param.getCourseId()));
    }

  }

  @Override
  public RealPageRuleEntity getRuleEntity() {
    return RealPaperRuleChooser.getInstance().chooseRule(getRuleEntityList());
  }

  @Override
  public RealPageRuleEntity transfer(QuesbkExamRule examRule) {
    return new RealPageRuleEntity(examRule);
  }

}
