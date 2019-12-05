package com.xiaodou.engine.rule.impl;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.domain.product.QuesbkExamRule;
import com.xiaodou.engine.rule.base.AbstractRule;
import com.xiaodou.engine.rule.base.RandomChooser;
import com.xiaodou.engine.rule.impl.ItemBreakthroughPaperRule.BreakthroughPaperRuleChooser;
import com.xiaodou.engine.rule.impl.ItemBreakthroughPaperRule.BreakthroughPaperRuleEntity;
import com.xiaodou.engine.rule.iterface.IRule;
import com.xiaodou.engine.rule.iterface.IRuleChooser;
import com.xiaodou.engine.rule.model.Rule;
import com.xiaodou.engine.rule.model.RuleEntity;
import com.xiaodou.engine.rule.model.RuleEntityParam;
import com.xiaodou.util.QuesbkUtil;
import com.xiaodou.util.QuesbkUtil.QuesNum;
import com.xiaodou.util.StaticInfoProp;

/**
 * @name BreakthroughPaperRule
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月9日
 * @description 节练习出题规则
 * @version 1.0
 */
public class ItemBreakthroughPaperRule extends AbstractRule<BreakthroughPaperRuleEntity>
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
    protected void _fetchRule(RuleEntityParam param, Rule rule) {
      rule.setProductChapterId(Integer.parseInt(param.getItemId()));
    }

    @Override
    public List<Rule> initDefaultRule(RuleEntityParam param) {
      List<Rule> ruleLst = Lists.newArrayList();
      Rule rule = new Rule();
      QuesNum qusNum;
      if (StringUtils.isNotBlank(param.getItemId())) {
        qusNum = QuesbkUtil.getQuesNum(QuesBaseConstant.BREAKTHROUGH_ITEM_DEFAULT_QUESNUM);
        rule.setProductChapterId(Integer.parseInt(param.getItemId()));
      } else {
        // 查询子章节列表
        qusNum = QuesbkUtil.getQuesNum(QuesBaseConstant.BREAKTHROUGH_CHAPTER_DEFAULT_QUESNUM);
        List<CourseProductItem> itemList =
            param.getService().queryItemList(param.getCourseId(), param.getChapterId());
        if (null == itemList || itemList.size() == 0) {
          throw new RuntimeException("章节信息不存在");
        }
        rule.setProductChapterId(null);
        rule.setItemList(Lists.transform(itemList, new Function<CourseProductItem, String>() {
          @Override
          public String apply(CourseProductItem item) {
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
