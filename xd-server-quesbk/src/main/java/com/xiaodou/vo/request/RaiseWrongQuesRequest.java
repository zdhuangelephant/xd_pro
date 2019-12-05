package com.xiaodou.vo.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class RaiseWrongQuesRequest extends QuesBasePojo {
  /**
   * 课程id
   * 
   */
  @NotEmpty
  private String courseId;
  /**
   * 题目ID
   * 
   */
  @NotEmpty
  private String quesId;
  /**
   * 错误类型 1、有错别字2、内容有错3、答案有错4、其它
   *  传入数组
   */
  @NotEmpty
  private String wrongType;
  /**
   * 错误描述
   * 
   */
  private String wrongMsg;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getQuesId() {
    return quesId;
  }

  public void setQuesId(String quesId) {
    this.quesId = quesId;
  }

  public String getWrongType() {
    return wrongType;
  }

  public void setWrongType(String wrongType) {
    this.wrongType = wrongType;
  }

  public String getWrongMsg() {
    return wrongMsg;
  }

  public void setWrongMsg(String wrongMsg) {
    this.wrongMsg = wrongMsg;
  }
}
