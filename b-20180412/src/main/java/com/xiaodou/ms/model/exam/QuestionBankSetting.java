package com.xiaodou.ms.model.exam;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Created by zyp on 15/7/27.
 */
public class QuestionBankSetting {

  // 总分数
  private Integer totalScore = 100;

  // 题目类型列表
  private List<QuestionBankQuestionType> typeList = Lists.newArrayList();

  public Integer getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(Integer totalScore) {
    this.totalScore = totalScore;
  }

  public List<QuestionBankQuestionType> getTypeList() {
    return typeList;
  }

  public void setTypeList(List<QuestionBankQuestionType> typeList) {
    this.typeList = typeList;
  }
}
