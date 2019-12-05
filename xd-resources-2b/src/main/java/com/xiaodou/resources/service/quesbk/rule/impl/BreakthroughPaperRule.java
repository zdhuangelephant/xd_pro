package com.xiaodou.resources.service.quesbk.rule.impl;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.model.quesbk.QuesbkExamRule;
import com.xiaodou.resources.service.quesbk.rule.base.AbstractRule;
import com.xiaodou.resources.service.quesbk.rule.base.RandomChooser;
import com.xiaodou.resources.service.quesbk.rule.impl.BreakthroughPaperRule.BreakthroughPaperRuleChooser;
import com.xiaodou.resources.service.quesbk.rule.impl.BreakthroughPaperRule.BreakthroughPaperRuleEntity;
import com.xiaodou.resources.service.quesbk.rule.iterface.IRule;
import com.xiaodou.resources.service.quesbk.rule.iterface.IRuleChooser;
import com.xiaodou.resources.service.quesbk.rule.model.Rule;
import com.xiaodou.resources.service.quesbk.rule.model.RuleEntity;
import com.xiaodou.resources.service.quesbk.rule.model.RuleEntityParam;
import com.xiaodou.resources.util.QuesbkUtil;
import com.xiaodou.resources.util.QuesbkUtil.QuesNum;
import com.xiaodou.resources.util.StaticInfoProp;

/**
 * @name BreakthroughPaperRule
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月9日
 * @description 章节练习出题规则
 * @version 1.0
 */
public class BreakthroughPaperRule extends AbstractRule<BreakthroughPaperRuleEntity>
    implements
      IRule<BreakthroughPaperRuleChooser, BreakthroughPaperRuleEntity> {

  /**
   * @name BreakthroughPaperRuleChooser
   * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月9日
   * @description 章节练习规则选择器
   * @version 1.0
   */
  static class BreakthroughPaperRuleChooser extends RandomChooser<BreakthroughPaperRuleEntity>
      implements
        IRuleChooser<BreakthroughPaperRuleEntity> {
    private static final BreakthroughPaperRuleChooser _instance =
        new BreakthroughPaperRuleChooser();

    private static BreakthroughPaperRuleChooser getInstance() {
      return _instance;
    }

    @Override
    public BreakthroughPaperRuleEntity chooseRule(List<BreakthroughPaperRuleEntity> ruleList) {
      if (null != ruleList && ruleList.size() == 1) return ruleList.get(0);
      return choose(ruleList);
    }

  }

  /**
   * @name BreakthroughPaperRuleEntity
   * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月9日
   * @description 章节练习规则实体
   * @version 1.0
   */
  class BreakthroughPaperRuleEntity extends RuleEntity {
    public BreakthroughPaperRuleEntity(QuesbkExamRule examRule) {
      super(examRule);
    }

    @Override
    public List<Rule> initDefaultRule(RuleEntityParam param) {
      List<Rule> ruleLst = Lists.newArrayList();
      Rule rule = new Rule();
      QuesNum qusNum = QuesbkUtil.getQuesNum(QuesBaseConstant.BREAKTHROUGH_DEFAULT_QUESNUM);
      if (StringUtils.isNotBlank(param.getItemId())) {
        rule.setProductChapterId(Integer.parseInt(param.getItemId()));
      } else {
        // 查询子章节列表
        List<ProductItemModel> itemList =
            param.getService().queryItemList(param.getCourseId(), param.getChapterId());
        if (null == itemList || itemList.size() == 0) {
          throw new RuntimeException("该章节不存在");
        }
        rule.setProductChapterId(null);
        rule.setItemList(Lists.transform(itemList, new Function<ProductItemModel, String>() {
          @Override
          public String apply(ProductItemModel item) {
            if (null != item && null != item.getId()) return item.getId().toString();
            return Integer.toString(Integer.MIN_VALUE);
          }
        }));
      }
      rule.setQuestionType(StaticInfoProp.getInt(QuesBaseConstant.QUES_TYPE_SIMPLE_CHOICE));
      rule.setQuestionNum(qusNum.getQuesNum());
      rule.setUserId(param.getUid());
      ruleLst.add(rule);
      return ruleLst;
    }
  }


  @Override
  public BreakthroughPaperRuleEntity getRuleEntity() {
    return BreakthroughPaperRuleChooser.getInstance().chooseRule(getRuleEntityList());
  }

  @Override
  public BreakthroughPaperRuleEntity transfer(QuesbkExamRule examRule) {
    return new BreakthroughPaperRuleEntity(examRule);
  }
}
