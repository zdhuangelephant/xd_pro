package com.xiaodou.engine.rule.factory;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.domain.product.QuesbkExamPaper;
import com.xiaodou.domain.product.QuesbkExamRule;
import com.xiaodou.engine.rule.base.AbstractRule;
import com.xiaodou.engine.rule.impl.ChallengePaperRule;
import com.xiaodou.engine.rule.impl.ChapterBreakthroughPaperRule;
import com.xiaodou.engine.rule.impl.ChapterPaperRule;
import com.xiaodou.engine.rule.impl.DailyPracticePaperRule;
import com.xiaodou.engine.rule.impl.FinalExamPaperRule;
import com.xiaodou.engine.rule.impl.ItemBreakthroughPaperRule;
import com.xiaodou.engine.rule.impl.LeakFillingPaperRule;
import com.xiaodou.engine.rule.impl.RealPaperRule;
import com.xiaodou.engine.rule.iterface.IRule;
import com.xiaodou.engine.rule.iterface.IRuleEntity;
import com.xiaodou.enums.ExamType;

public class RuleFactory {
  @SuppressWarnings("rawtypes")
  enum ExamTypeRule {
    REAL_PAPER(ExamType.REAL_PAPER, RealPaperRule.class), CHAPTER_PAPER(ExamType.CHAPTER_PAPER,
        ChapterPaperRule.class), CHAPTER_BREAKTHROUGH_PAPER(ExamType.CHAPTER_BREAKTHROUGH_PAPER,
        ChapterBreakthroughPaperRule.class), ITEM_BREAKTHROUGH_PAPER(
        ExamType.ITEM_BREAKTHROUGH_PAPER, ItemBreakthroughPaperRule.class), RANDOM_CHALLENGE_PAPER(
        ExamType.RANDOM_CHALLENGE_PAPER, ChallengePaperRule.class), FRIEND_CHALLENGE_PAPER(
        ExamType.FRIEND_CHALLENGE_PAPER, ChallengePaperRule.class), DAILY_PRACTICE(
        ExamType.DAILY_PRACTICE, DailyPracticePaperRule.class), FINAL_EXAM_PAPER(
        ExamType.FINAL_EXAM_PAPER, FinalExamPaperRule.class), LEAK_FILLING(ExamType.LEAK_FILLING,
        LeakFillingPaperRule.class);
    private ExamType type;
    private Class<? extends AbstractRule> rule;

    public ExamType getType() {
      return type;
    }

    public Class<? extends AbstractRule> getRule() {
      return rule;
    }

    ExamTypeRule(ExamType type, Class<? extends AbstractRule> rule) {
      this.type = type;
      this.rule = rule;
    }

    private final static Map<ExamType, Class<? extends AbstractRule>> _allExamRule = Maps
        .newHashMap();

    private static void init() {
      for (ExamTypeRule examRule : ExamTypeRule.values()) {
        if (null == examRule) continue;
        _allExamRule.put(examRule.getType(), examRule.getRule());
      }
    }

    static {
      init();
    }

    public static Class<? extends AbstractRule> getRuleType(ExamType type) {
      return _allExamRule.get(type);
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static IRuleEntity getRule(ExamType type, List<QuesbkExamRule> examRuleList,
      QuesbkExamPaper paper) throws InstantiationException, IllegalAccessException {
    Class<? extends AbstractRule> clazz = ExamTypeRule.getRuleType(type);
    if (null == clazz) return null;
    AbstractRule rule = clazz.newInstance();
    rule.setPaper(paper);
    rule.setRuleEntityList(examRuleList);
    return ((IRule) rule).getRuleEntity();
  }

  @SuppressWarnings("rawtypes")
  public static IRuleEntity getRule(String typeName, List<QuesbkExamRule> examRuleList,
      QuesbkExamPaper paper) throws InstantiationException, IllegalAccessException {
    if (StringUtils.isJsonNotBlank(paper.getQuesIds()))
      return getRule(ExamType.REAL_PAPER, examRuleList, paper);
    return getRule(ExamType.getByCode(typeName), examRuleList, paper);
  }
}
