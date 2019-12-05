package com.xiaodou.course.web.request.notes;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class NotesListRequest extends BaseRequest {
  //笔记最后一条id
  private String notesId;
  /* 课程状态 1:有效2：无效 */
  @NotEmpty
  private String courseStatus;

  public String getNotesId() {
    return notesId;
  }

  public void setNotesId(String notesId) {
    this.notesId = notesId;
  }

  public String getCourseStatus() {
    return courseStatus;
  }

  public void setCourseStatus(String courseStatus) {
    this.courseStatus = courseStatus;
  }
}
