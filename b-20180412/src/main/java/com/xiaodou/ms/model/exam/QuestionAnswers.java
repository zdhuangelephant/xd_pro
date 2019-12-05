package com.xiaodou.ms.model.exam;

import java.util.List;

/**
 * Created by zyp on 15/8/7.
 */
public class QuestionAnswers {

  /**
   * 答案类型
   */
  private Integer optionType;

  /**
   * 答案列表
   */
  private List<QuestionSelection> questionSelectionList;

  public Integer getOptionType() {
    return optionType;
  }

  public void setOptionType(Integer optionType) {
    this.optionType = optionType;
  }

  public List<QuestionSelection> getQuestionSelectionList() {
    return questionSelectionList;
  }

  public void setQuestionSelectionList(List<QuestionSelection> questionSelectionList) {
    this.questionSelectionList = questionSelectionList;
  }
}
