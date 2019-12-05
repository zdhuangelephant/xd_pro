package com.xiaodou.resources.response.quesbk;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.constant.quesbk.ResultType;
import com.xiaodou.resources.model.quesbk.QuesbkAnswer;
import com.xiaodou.resources.response.BaseResponse;

public class QuesDetailResponse extends BaseResponse {

  public QuesDetailResponse(ResultType type) {
    super(type);
  }

  /**
   * 问题ID
   */
  private String quesId;
  /**
   * 问题类型
   */
  private String quesType;
  /**
   * 问题描述
   */
  private String quesDesc;
  /**
   * 考点列表
   */
  private List<KeyWord> keyPointList = Lists.newArrayList();
  /**
   * 难度
   */
  private String diffculty;
  /**
   * 正确答案ID
   */
  private String rightAnswerId;
  /**
   * 问题答案列表
   */
  private List<Answer> answerList = Lists.newArrayList();
  /**
   * 问题解析
   */
  private String quesAnaly;

  public String getQuesId() {
    return quesId;
  }

  public void setQuesId(String quesId) {
    this.quesId = quesId;
  }

  public String getQuesType() {
    return quesType;
  }

  public void setQuesType(String quesType) {
    this.quesType = quesType;
  }

  public String getQuesDesc() {
    return quesDesc;
  }

  public void setQuesDesc(String quesDesc) {
    this.quesDesc = quesDesc;
  }

  public List<KeyWord> getKeyPointList() {
    return keyPointList;
  }

  public void setKeyPointList(List<KeyWord> keyPointList) {
    this.keyPointList = keyPointList;
  }

  public String getDiffculty() {
    return diffculty;
  }

  public void setDiffculty(String diffculty) {
    this.diffculty = diffculty;
  }

  public String getRightAnswerId() {
    return rightAnswerId;
  }

  public void setRightAnswerId(String rightAnswerId) {
    this.rightAnswerId = rightAnswerId;
  }

  public List<Answer> getAnswerList() {
    return answerList;
  }

  public void setAnswerList(List<Answer> answerList) {
    this.answerList = answerList;
  }

  public String getQuesAnaly() {
    return quesAnaly;
  }

  public void setQuesAnaly(String quesAnaly) {
    this.quesAnaly = quesAnaly;
  }

  public static class Answer implements Comparable<Answer> {
    public Answer() {}

    public Answer(QuesbkAnswer qanswer) {
      setAnswerDesc(qanswer.getSelection());
      setAnswerId(qanswer.getId().toString());
      setAnswerImgUrl(qanswer.getImgUrl());
    }

    @Override
    public int compareTo(Answer o) {
      return this.getAnswerId().compareTo(o.getAnswerId());
    }

    /**
     * 答案ID
     */
    private String answerId;
    /**
     * 答案详情
     */
    private String answerDesc;
    /**
     * 答案图片Url
     */
    private String answerImgUrl;

    public String getAnswerImgUrl() {
      return answerImgUrl;
    }

    public void setAnswerImgUrl(String answerImgUrl) {
      this.answerImgUrl = answerImgUrl;
    }

    public String getAnswerId() {
      return answerId;
    }

    public void setAnswerId(String answerId) {
      this.answerId = answerId;
    }

    public String getAnswerDesc() {
      return answerDesc;
    }

    public void setAnswerDesc(String answerDesc) {
      this.answerDesc = answerDesc;
    }
  }

  /**
   * 考点
   * 
   * @author Administrator
   */
  public static class KeyWord {
    /**
     * 考点ID
     */
    private String poindId;
    /**
     * 考点名称
     */
    private String name;

    public String getPoindId() {
      return poindId;
    }

    public void setPoindId(String poindId) {
      this.poindId = poindId;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

}
