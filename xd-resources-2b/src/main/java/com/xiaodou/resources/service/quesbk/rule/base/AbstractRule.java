package com.xiaodou.resources.service.quesbk.rule.base;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.resources.model.quesbk.QuesbkExamPaper;
import com.xiaodou.resources.model.quesbk.QuesbkExamRule;

public abstract class AbstractRule<T> {

  private QuesbkExamPaper paper;

  private List<T> ruleEntityList = Lists.newArrayList();

  public List<T> getRuleEntityList() {
    return ruleEntityList;
  }

  public void setRuleEntityList(List<QuesbkExamRule> examRuleList) {
    if (null == examRuleList || examRuleList.size() == 0)
      ruleEntityList.add(transfer(null));
    else
      ruleEntityList.addAll(Lists.transform(examRuleList, new Function<QuesbkExamRule, T>() {
        @Override
        public T apply(QuesbkExamRule examRule) {
          return transfer(examRule);
        }
      }));
  }

  public QuesbkExamPaper getPaper() {
    return paper;
  }

  public void setPaper(QuesbkExamPaper paper) {
    this.paper = paper;
  }

  public AbstractRule() {}

  /**
   * 转换examRule实体为具体rule
   */
  public abstract T transfer(QuesbkExamRule examRule);
}
