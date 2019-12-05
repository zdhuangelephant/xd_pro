package com.xiaodou.engine.rule.impl;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.domain.product.FinalExamModel;
import com.xiaodou.domain.product.QuesbkExamRule;
import com.xiaodou.engine.rule.base.AbstractRule;
import com.xiaodou.engine.rule.base.RandomChooser;
import com.xiaodou.engine.rule.impl.FinalExamPaperRule.FinalExamPaperRuleChooser;
import com.xiaodou.engine.rule.impl.FinalExamPaperRule.FinalExamPaperRuleEntity;
import com.xiaodou.engine.rule.iterface.IRule;
import com.xiaodou.engine.rule.iterface.IRuleChooser;
import com.xiaodou.engine.rule.model.Rule;
import com.xiaodou.engine.rule.model.RuleEntity;
import com.xiaodou.engine.rule.model.RuleEntityParam;
import com.xiaodou.manager.facade.QuesOperationFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.util.StaticInfoProp;

/**
 * @name ChapterPaperRule
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月9日
 * @description 章节练习出题规则
 * @version 1.0
 */
public class FinalExamPaperRule extends AbstractRule<FinalExamPaperRuleEntity>
    implements
      IRule<FinalExamPaperRuleChooser, FinalExamPaperRuleEntity> {
  /**
   * @name FinalExamPaperRuleChooser
   * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月9日
   * @description 章节练习规则选择器
   * @version 1.0
   */
  static class FinalExamPaperRuleChooser extends RandomChooser<FinalExamPaperRuleEntity>
      implements
        IRuleChooser<FinalExamPaperRuleEntity> {
    private static final FinalExamPaperRuleChooser _instance = new FinalExamPaperRuleChooser();

    private static FinalExamPaperRuleChooser getInstance() {
      return _instance;
    }

    @Override
    public FinalExamPaperRuleEntity chooseRule(List<FinalExamPaperRuleEntity> ruleList) {
      if (null != ruleList && ruleList.size() == 1) return ruleList.get(0);
      return choose(ruleList);
    }

  }

  /**
   * @name FinalExamPaperRuleEntity
   * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月9日
   * @description 章节练习规则实体
   * @version 1.0
   */
  class FinalExamPaperRuleEntity extends RuleEntity {
    public FinalExamPaperRuleEntity(QuesbkExamRule examRule) {
      super(examRule);
    }

    @Override
    protected void _fetchRule(RuleEntityParam param, Rule rule) {
      if (StringUtils.isNotBlank(getPaper().getQuesIds())) return;
      List<CourseProductItem> itemList =
          param.getService().queryChapterItemList(param.getCourseId());
      if (null == itemList || itemList.size() == 0) {
        throw new RuntimeException("章节信息不存在");
      }
      Integer num = 10;
      if (StringUtils.isNotBlank(param.getItemId())) {
        Long id = Long.valueOf(param.getItemId());
        QuesOperationFacade quesOperationFacade =
            SpringWebContextHolder.getBean("quesOperationFacade");
        FinalExamModel finalExam = quesOperationFacade.selectFinalExamById(id);
        if (finalExam != null) {
          num = finalExam.getQuestionNums();
        }
      }

      rule.setItemList(Lists.transform(itemList, new Function<CourseProductItem, String>() {
        @Override
        public String apply(CourseProductItem item) {
          if (null != item && null != item.getId()) return item.getId().toString();
          return Integer.toString(Integer.MIN_VALUE);
        }
      }));
      rule.setQuestionNum(num);
      rule.setUserId(param.getUid());
    }

    @Override
    public List<Rule> initDefaultRule(RuleEntityParam param) {
      if (StringUtils.isNotBlank(getPaper().getQuesIds())) return null;
      List<Rule> ruleLst = Lists.newArrayList();
      List<CourseProductItem> itemList =
          param.getService().queryChapterItemList(param.getCourseId());
      if (null == itemList || itemList.size() == 0) {
        throw new RuntimeException("章节信息不存在");
      }
      Integer num = 10;
      if (StringUtils.isNotBlank(param.getItemId())) {
        Long id = Long.valueOf(param.getItemId());
        QuesOperationFacade quesOperationFacade =
            SpringWebContextHolder.getBean("quesOperationFacade");
        FinalExamModel finalExam = quesOperationFacade.selectFinalExamById(id);
        if (finalExam != null) {
          num = finalExam.getQuestionNums();
        }
      }

      Rule rule = new Rule();
      rule.setItemList(Lists.transform(itemList, new Function<CourseProductItem, String>() {
        @Override
        public String apply(CourseProductItem item) {
          if (null != item && null != item.getId()) return item.getId().toString();
          return Integer.toString(Integer.MIN_VALUE);
        }
      }));
      rule.setQuestionType(StaticInfoProp.getInt(QuesBaseConstant.QUES_TYPE_SIMPLE_CHOICE));
      rule.setQuestionNum(num);
      rule.setUserId(param.getUid());
      ruleLst.add(rule);
      return ruleLst;
    }
  }



  @Override
  public FinalExamPaperRuleEntity getRuleEntity() {
    return FinalExamPaperRuleChooser.getInstance().chooseRule(getRuleEntityList());
  }

  @Override
  public FinalExamPaperRuleEntity transfer(QuesbkExamRule examRule) {
    return new FinalExamPaperRuleEntity(examRule);
  }
}
