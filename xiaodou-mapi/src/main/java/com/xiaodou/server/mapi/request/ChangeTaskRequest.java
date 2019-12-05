package com.xiaodou.server.mapi.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class ChangeTaskRequest extends MapiBaseRequest {

  @NotEmpty
  private String taskId;

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }
  
  
}
