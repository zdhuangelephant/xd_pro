package com.xiaodou.domain;

import java.util.List;

import com.google.common.collect.Lists;

import edu.emory.mathcs.backport.java.util.Collections;

public class QuesbkAnswerList extends BaseEntity {

  private String optionType;

  private List<QuesbkAnswer> questionSelectionList = Lists.newArrayList();

  public String getOptionType() {
    return optionType;
  }

  public void setOptionType(String optionType) {
    this.optionType = optionType;
  }

  public void shuffleAnswer() {
    if (null != this.questionSelectionList && !this.questionSelectionList.isEmpty())
      Collections.shuffle(questionSelectionList);
  }

  public List<QuesbkAnswer> getQuestionSelectionList() {
    return questionSelectionList;
  }

  public void setQuestionSelectionList(List<QuesbkAnswer> questionSelectionList) {
    this.questionSelectionList = questionSelectionList;
  }

}