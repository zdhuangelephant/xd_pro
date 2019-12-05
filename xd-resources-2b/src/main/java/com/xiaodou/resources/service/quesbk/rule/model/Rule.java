package com.xiaodou.resources.service.quesbk.rule.model;

import java.util.List;

import com.xiaodou.resources.util.QuesbkUtil;

/**
 * @name Rule CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月8日
 * @description 規則細節
 * @version 1.0
 */
public class Rule {
  /** 节ID */
  private Integer productChapterId;
  /** chapterIds 所属节ID */
  private String chapterIds;
  /** itemList 节ID列表 */
  private List<String> itemList;
  /** 问题类型 */
  private Integer questionType;
  /** 难度 */
  private Short easyLevel;
  /** 认知程度 */
  private Short cognitionLevel;
  /** zhenti 是否为真题 */
  private Short zhenti;
  /** 数量 */
  private Integer questionNum;
  /** itemType 章节类型 */
  private Short itemType;
  /** userId userId */
  private String userId;

  public Rule() {}

  public Rule(Rule rule) {
    this.productChapterId = rule.productChapterId;
    this.questionType = rule.questionType;
    this.questionNum = rule.questionNum;
    this.easyLevel = rule.easyLevel;
    this.cognitionLevel = rule.cognitionLevel;
    this.zhenti = rule.zhenti;
    this.itemType = rule.itemType;
    this.userId = rule.userId;
  }

  public List<String> getItemList() {
    return itemList;
  }

  public void setItemList(List<String> itemList) {
    this.itemList = itemList;
  }

  public String getChapterIds() {
    return chapterIds;
  }

  public void setChapterIds(String chapterIds) {
    this.chapterIds = chapterIds;
  }

  public Short getZhenti() {
    return zhenti;
  }

  public void setZhenti(Short zhenti) {
    this.zhenti = zhenti;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Integer getProductChapterId() {
    return productChapterId;
  }

  public void setProductChapterId(Integer productChapterId) {
    this.productChapterId = productChapterId;
  }

  public Integer getQuestionType() {
    return questionType;
  }

  public void setQuestionType(Integer questionType) {
    this.questionType = questionType;
  }

  public Short getEasyLevel() {
    return easyLevel;
  }

  public void setEasyLevel(Short easyLevel) {
    this.easyLevel = easyLevel;
  }

  public Short getCognitionLevel() {
    return cognitionLevel;
  }

  public void setCognitionLevel(Short cognitionLevel) {
    this.cognitionLevel = cognitionLevel;
  }

  public Integer getQuestionNum() {
    return questionNum;
  }

  public void setQuestionNum(Integer questionNum) {
    this.questionNum = questionNum;
  }

  public void setQuestionNum(String questionNum) {
    String[] _questionNum = questionNum.split("~");
    if (_questionNum.length == 1)
      this.questionNum = Integer.parseInt(questionNum);
    else
      this.questionNum =
          new QuesbkUtil.QuesNum(Integer.parseInt(_questionNum[0]),
              Integer.parseInt(_questionNum[1])).getQuesNum();
  }

  public Short getItemType() {
    return itemType;
  }

  public void setItemType(Short itemType) {
    this.itemType = itemType;
  }

}
