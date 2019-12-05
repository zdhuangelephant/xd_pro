package com.xiaodou.engine.rule.impl;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.domain.product.QuesbkExamRule;
import com.xiaodou.engine.rule.base.AbstractRule;
import com.xiaodou.engine.rule.base.RandomChooser;
import com.xiaodou.engine.rule.impl.AptitudePaperRule.AptitudePageRuleEntity;
import com.xiaodou.engine.rule.impl.AptitudePaperRule.AptitudePaperRuleChooser;
import com.xiaodou.engine.rule.iterface.IRule;
import com.xiaodou.engine.rule.iterface.IRuleChooser;
import com.xiaodou.engine.rule.model.Rule;
import com.xiaodou.engine.rule.model.RuleEntity;
import com.xiaodou.engine.rule.model.RuleEntityParam;
import com.xiaodou.util.QuesbkUtil;
import com.xiaodou.util.QuesbkUtil.QuesNum;
import com.xiaodou.util.StaticInfoProp;

/**
 * @name AptitudePaperRule
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月8日
 * @description 智能练习出题规则
 * @version 1.0
 */
public class AptitudePaperRule extends AbstractRule<AptitudePageRuleEntity>
    implements
      IRule<AptitudePaperRuleChooser, AptitudePageRuleEntity> {

  /**
   * @name AptitudePaperRuleChooser
   * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月8日
   * @description 智能练习规则选择器
   * @version 1.0
   */
  static class AptitudePaperRuleChooser extends RandomChooser<AptitudePageRuleEntity>
      implements
        IRuleChooser<AptitudePageRuleEntity> {
    private static final AptitudePaperRuleChooser _instance = new AptitudePaperRuleChooser();

    private static AptitudePaperRuleChooser getInstance() {
      return _instance;
    }

    @Override
    public AptitudePageRuleEntity chooseRule(List<AptitudePageRuleEntity> ruleList) {
      if (null != ruleList && ruleList.size() == 1) return ruleList.get(0);
      return choose(ruleList);
    }
  }

  /**
   * @name AptitudePageRuleEntity
   * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月8日
   * @description 智能练习规则实体
   * @version 1.0
   */
  class AptitudePageRuleEntity extends RuleEntity {
    public AptitudePageRuleEntity(QuesbkExamRule examRule) {
      super(examRule);
    }

    @Override
    protected void _fetchRule(RuleEntityParam param, Rule rule) {
      List<CourseProductItem> itemList =
          param.getService().queryChapterItemList(param.getCourseId());
      if (null == itemList || itemList.size() == 0) {
        throw new RuntimeException("章节信息不存在");
      }
      rule.setItemList(Lists.transform(itemList, new Function<CourseProductItem, String>() {
        @Override
        public String apply(CourseProductItem item) {
          if (null != item && null != item.getId()) return item.getId().toString();
          return Integer.toString(Integer.MIN_VALUE);
        }
      }));
    }

    @Override
    public List<Rule> initDefaultRule(RuleEntityParam param) {
      List<Rule> ruleLst = Lists.newArrayList();
      List<CourseProductItem> itemList =
          param.getService().queryChapterItemList(param.getCourseId());
      if (null == itemList || itemList.size() == 0) {
        throw new RuntimeException("章节信息不存在");
      }
      QuesNum qusNum = QuesbkUtil.getQuesNum(QuesBaseConstant.APTITUDE_DEFAULT_QUESNUM);
      Rule rule = new Rule();
      rule.setItemList(Lists.transform(itemList, new Function<CourseProductItem, String>() {
        @Override
        public String apply(CourseProductItem item) {
          if (null != item && null != item.getId()) return item.getId().toString();
          return Integer.toString(Integer.MIN_VALUE);
        }
      }));
      rule.setQuestionType(StaticInfoProp.getInt(QuesBaseConstant.QUES_TYPE_SIMPLE_CHOICE));
      rule.setQuestionNum(qusNum.getQuesNum());
      rule.setUserId(param.getUid());
      ruleLst.add(rule);
      return ruleLst;
    }
  }

  @Override
  public AptitudePageRuleEntity getRuleEntity() {
    return AptitudePaperRuleChooser.getInstance().chooseRule(getRuleEntityList());
  }

  @Override
  public AptitudePageRuleEntity transfer(QuesbkExamRule examRule) {
    return new AptitudePageRuleEntity(examRule);
  }

}
