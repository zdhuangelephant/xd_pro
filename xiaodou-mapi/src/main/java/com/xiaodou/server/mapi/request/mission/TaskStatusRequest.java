package com.xiaodou.server.mapi.request.mission;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.server.mapi.request.mission.TaskStatusRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月29日
 * @description 任务状态请求
 * @version 1.0
 */
public class TaskStatusRequest extends MapiBaseRequest {

  /** taskId 任务ID */
  @NotEmpty
  private String taskId;
  /** courseId 课程ID */
  @NotEmpty
  private String courseId;
  /** chapterId 章ID */
  @NotEmpty
  private String chapterId;
  /** recordId 任务记录ID */
  private String recordId;

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

}
