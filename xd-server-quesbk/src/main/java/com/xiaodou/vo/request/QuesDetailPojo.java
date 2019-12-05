package com.xiaodou.vo.request;


public class QuesDetailPojo extends QuesBasePojo {

  /**
   * 课程ID
   */
  private String subjectId;
  /**
   * 问题ID
   */
  private String quesId;

  public String getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(String subjectId) {
    this.subjectId = subjectId;
  }

  public String getQuesId() {
    return quesId;
  }

  public void setQuesId(String quesId) {
    this.quesId = quesId;
  }

}
