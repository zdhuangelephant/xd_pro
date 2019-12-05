package com.xiaodou.resources.request.quesbk;

import com.xiaodou.resources.request.BaseRequest;


public class QuesDetailPojo extends BaseRequest {

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
