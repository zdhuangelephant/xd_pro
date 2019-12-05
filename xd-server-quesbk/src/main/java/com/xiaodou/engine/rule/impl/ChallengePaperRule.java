package com.xiaodou.engine.rule.impl;

import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.domain.product.QuesbkExamRule;
import com.xiaodou.domain.product.QuesbkQues;
import com.xiaodou.engine.rule.base.AbstractRule;
import com.xiaodou.engine.rule.base.RandomChooser;
import com.xiaodou.engine.rule.impl.ChallengePaperRule.ChallengePaperRuleChooser;
import com.xiaodou.engine.rule.impl.ChallengePaperRule.ChallengeRuleEntity;
import com.xiaodou.engine.rule.iterface.IRule;
import com.xiaodou.engine.rule.iterface.IRuleChooser;
import com.xiaodou.engine.rule.model.Rule;
import com.xiaodou.engine.rule.model.RuleEntity;
import com.xiaodou.engine.rule.model.RuleEntityParam;
import com.xiaodou.util.QuesbkUtil;
import com.xiaodou.util.QuesbkUtil.QuesNum;
import com.xiaodou.util.StaticInfoProp;

public class ChallengePaperRule extends AbstractRule<ChallengeRuleEntity>
    implements
      IRule<ChallengePaperRuleChooser, ChallengeRuleEntity> {

  static class ChallengePaperRuleChooser extends RandomChooser<ChallengeRuleEntity>
      implements
        IRuleChooser<ChallengeRuleEntity> {
    private static final ChallengePaperRuleChooser _instance = new ChallengePaperRuleChooser();

    private static ChallengePaperRuleChooser getInstance() {
      return _instance;
    }

    @Override
    public ChallengeRuleEntity chooseRule(List<ChallengeRuleEntity> ruleList) {
      if (null != ruleList && ruleList.size() == 1) return ruleList.get(0);
      return choose(ruleList);
    }
  }

  class ChallengeRuleEntity extends RuleEntity {

    public ChallengeRuleEntity(QuesbkExamRule examRule) {
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
      rule.setItemList(Lists.transform(itemList, new Function<CourseProductItem, String>() {
        @Override
        public String apply(CourseProductItem item) {
          if (null != item && null != item.getId()) return item.getId().toString();
          return Integer.toString(Integer.MIN_VALUE);
        }
      }));

    }

    @Override
    protected List<Rule> initDefaultRule(RuleEntityParam param) {
      if (StringUtils.isNotBlank(getPaper().getQuesIds())) return null;
      List<Rule> ruleLst = Lists.newArrayList();
      List<CourseProductItem> itemList =
          param.getService().queryChapterItemList(param.getCourseId());
      if (null == itemList || itemList.size() == 0) {
        throw new RuntimeException("章节信息不存在");
      }
      QuesNum qusNum = QuesbkUtil.getQuesNum(QuesBaseConstant.CHALLENGE_DEFAULT_QUESNUM);
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

    @Override
    protected void processRuleList(RuleEntityParam param, List<QuesbkQues> quesLst,
        List<Rule> ruleList, String uid) throws InterruptedException {
      if (StringUtils.isNotBlank(getPaper().getQuesIds()))
        processRule(null, param, quesLst, uid);
      else
        super.processRuleList(param, quesLst, ruleList, uid);
    }

    @Override
    protected void processRule(Rule rule, RuleEntityParam param, List<QuesbkQues> quesLst,
        String uid) {
      if (StringUtils.isNotBlank(getPaper().getQuesIds())) {
        List<String> quesIdLst =
            FastJsonUtil.fromJsons(getPaper().getQuesIds(), new TypeReference<List<String>>() {});
        if (null == quesIdLst || quesIdLst.size() == 0) return;
        quesLst.addAll(param.getService().queryQuesList(quesIdLst, uid, param.getCourseId()));
      } else {
        super.processRule(rule, param, quesLst, uid);
      }
    }

  }

  @Override
  public ChallengeRuleEntity getRuleEntity() {
    return ChallengePaperRuleChooser.getInstance().chooseRule(getRuleEntityList());
  }

  @Override
  public ChallengeRuleEntity transfer(QuesbkExamRule examRule) {
    return new ChallengeRuleEntity(examRule);
  }

}
