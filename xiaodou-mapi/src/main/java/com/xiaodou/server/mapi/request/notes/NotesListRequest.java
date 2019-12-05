package com.xiaodou.server.mapi.request.notes;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class NotesListRequest extends MapiBaseRequest {
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
