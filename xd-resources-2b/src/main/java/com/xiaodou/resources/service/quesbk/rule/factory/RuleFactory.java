package com.xiaodou.resources.service.quesbk.rule.factory;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.resources.enums.quesbk.ExamType;
import com.xiaodou.resources.model.quesbk.QuesbkExamPaper;
import com.xiaodou.resources.model.quesbk.QuesbkExamRule;
import com.xiaodou.resources.service.quesbk.rule.base.AbstractRule;
import com.xiaodou.resources.service.quesbk.rule.impl.AptitudePaperRule;
import com.xiaodou.resources.service.quesbk.rule.impl.BreakthroughPaperRule;
import com.xiaodou.resources.service.quesbk.rule.impl.ChapterPaperRule;
import com.xiaodou.resources.service.quesbk.rule.impl.HomeWorkPaperRule;
import com.xiaodou.resources.service.quesbk.rule.impl.RealPaperRule;
import com.xiaodou.resources.service.quesbk.rule.impl.SimulatePaperRule;
import com.xiaodou.resources.service.quesbk.rule.iterface.IRule;
import com.xiaodou.resources.service.quesbk.rule.iterface.IRuleEntity;

public class RuleFactory {
  @SuppressWarnings("rawtypes")
  enum ExamTypeRule {
    REAL_PAPER(ExamType.REAL_PAPER, RealPaperRule.class), APTITUDE_PAPER(ExamType.APTITUDE_PAPER,
        AptitudePaperRule.class), SIMULATE_PAPER(ExamType.SIMULATE_PAPER, SimulatePaperRule.class), CHAPTER_PAPER(
        ExamType.CHAPTER_PAPER, ChapterPaperRule.class), HOMEWORK_PAPER(ExamType.HOMEWORK_PAPER,
        HomeWorkPaperRule.class), BREAKTHROUGH_PAPER(ExamType.BREAKTHROUGH_PAPER,
        BreakthroughPaperRule.class);
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
