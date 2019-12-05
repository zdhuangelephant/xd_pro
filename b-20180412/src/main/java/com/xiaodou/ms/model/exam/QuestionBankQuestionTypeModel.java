package com.xiaodou.ms.model.exam;

/**
 * Created by zyp on 15/6/26.
 */
public class QuestionBankQuestionTypeModel {

  /**
   * 单选题
   */
  public final static Integer Radio = 1;

  /**
   * 多选题
   */
  public final static Integer CheckBox = 2;

  /**
   * 题目类型
   */
  private Integer id;

  /**
   * 题目类型名称
   */
  private String typeName;

  /**
   * 状态
   */
  private String status;

  /**
   * 描述
   */
  private String mdesc;

  /**
   * 杂项
   */
  private String misc;

  /**
   * 0 客观题
   * 1 主观题
   */
  private Integer answerType;

  public Integer getAnswerType() {
    return answerType;
  }

  public void setAnswerType(Integer answerType) {
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMdesc() {
    return mdesc;
  }

  public void setMdesc(String mdesc) {
    this.mdesc = mdesc;
  }

  public String getMisc() {
    return misc;
  }

  public void setMisc(String misc) {
    this.misc = misc;
  }
}

