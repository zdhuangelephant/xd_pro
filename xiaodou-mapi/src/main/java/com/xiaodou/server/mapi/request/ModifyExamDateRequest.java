package com.xiaodou.server.mapi.request;

import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class ModifyExamDateRequest extends MapiBaseRequest {
  @NotEmpty
  private String courseId; // 课程ID
  @NotEmpty
  @LegalValueList({"1", "2"})
  private String moveType;// 考期移动 1 其它考期->最近考期 2 最近考期->其它考期

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getMoveType() {
    return moveType;
  }

  public void setMoveType(String moveType) {
    this.moveType = moveType;
  }
}
