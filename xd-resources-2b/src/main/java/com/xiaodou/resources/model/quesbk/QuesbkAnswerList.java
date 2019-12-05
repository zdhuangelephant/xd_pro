package com.xiaodou.resources.model.quesbk;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.model.BaseEntity;

public class QuesbkAnswerList extends BaseEntity {

  private String optionType;

  private List<QuesbkAnswer> questionSelectionList = Lists.newArrayList();

  public String getOptionType() {
    return optionType;
  }

  public void setOptionType(String optionType) {
    this.optionType = optionType;
  }

  public List<QuesbkAnswer> getQuestionSelectionList() {
    return questionSelectionList;
  }

  public void setQuestionSelectionList(List<QuesbkAnswer> questionSelectionList) {
    this.questionSelectionList = questionSelectionList;
  }


}
