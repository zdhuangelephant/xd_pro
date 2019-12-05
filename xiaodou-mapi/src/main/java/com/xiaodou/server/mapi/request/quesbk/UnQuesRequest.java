package com.xiaodou.server.mapi.request.quesbk;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class UnQuesRequest extends MapiBaseRequest {
  /**
   * 课程ID
   */
  @NotEmpty
  private String courseId;
  /**
   * 问题ID
   * 
   */
  @NotEmpty
  private String quesId;

  /** type 目标错题状态 */
  @NotEmpty
  @LegalValueList({"1", "2", "3", "4"})
  private String type;

  public String getQuesId() {
    return quesId;
  }

  public void setQuesId(String quesId) {
    this.quesId = quesId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

}
