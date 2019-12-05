package com.xiaodou.ms.model.exam;

/**
 * Created by zyp on 15/7/27.
 */
public class QuestionBankQuestionType {

  // 主键Id
  private Integer id;

  // 类型名
  private String typeName;

  // 分值
  private Integer score;

  // 排序
  private Integer listOrder;

  // 题目数
  private Integer questionNum;

  // 客观题or主观题
  private String answerType;

  public QuestionBankQuestionType() {}

  public QuestionBankQuestionType(QuestionBankQuestionTypeModel typeModel) {
    if (null == typeModel) return;
    this.id = typeModel.getId();
    this.typeName = typeModel.getTypeName();
    this.score = 10;
    this.listOrder = typeModel.getId();
    this.questionNum = 10;
    this.answerType = typeModel.getAnswerType().toString();
  }

  public String getAnswerType() {
    return answerType;
  }

  public void setAnswerType(String answerType) {
    this.answerType = answerType;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public Integer getListOrder() {
    return listOrder;
  }

  public void setListOrder(Integer listOrder) {
    this.listOrder = listOrder;
  }

  public Integer getQuestionNum() {
    return questionNum;
  }

  public void setQuestionNum(Integer questionNum) {
    this.questionNum = questionNum;
  }
}
