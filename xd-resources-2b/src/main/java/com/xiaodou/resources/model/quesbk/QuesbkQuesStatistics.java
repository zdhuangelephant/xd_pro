package com.xiaodou.resources.model.quesbk;

import com.xiaodou.resources.model.BaseEntity;
import com.xiaodou.resources.util.QuesbkUtil;

public class QuesbkQuesStatistics extends BaseEntity {

  public QuesbkQuesStatistics() {
    this.examTimes = 0;
    this.wrongTimes = 0;
    this.rightPercent = "0.00";
  }

  public void addTimes() {
    this.examTimes++;
    caculateRP();
  }

  public void addWrongTime() {
    this.wrongTimes++;
    addTimes();
  }

  private void caculateRP() {
    this.rightPercent =
        QuesbkUtil.getFormat().format(
            (new Double((examTimes - wrongTimes)) / new Double(examTimes)));
  }

  private Long id;

  private Long courseId;

  private Long questionId;

  private Integer examTimes;

  private Integer wrongTimes;

  private String rightPercent;

  private String answerDetail;

  public Integer getExamTimes() {
    return examTimes;
  }

  public void setExamTimes(Integer examTimes) {
    this.examTimes = examTimes;
  }

  public Integer getWrongTimes() {
    return wrongTimes;
  }

  public void setWrongTimes(Integer wrongTimes) {
    this.wrongTimes = wrongTimes;
  }

  public Long getId() {
    return id;
  }

  public Long getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Long questionId) {
    this.questionId = questionId;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public String getRightPercent() {
    return rightPercent;
  }

  public void setRightPercent(String rightPercent) {
    this.rightPercent = rightPercent == null ? null : rightPercent.trim();
  }

  public String getAnswerDetail() {
    return answerDetail;
  }

  public void setAnswerDetail(String answerDetail) {
    this.answerDetail = answerDetail == null ? null : answerDetail.trim();
  }

  public static class AnswerDetail {
    /**
     * 问题ID
     */
    private String id;
    /**
     * 作答次数
     */
    private Integer times;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public Integer getTimes() {
      return times;
    }

    public void setTimes(Integer times) {
      this.times = times;
    }

    public void addTimes() {
      this.times++;
    }
  }
}
