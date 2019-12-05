package com.xiaodou.mission.vo.response;

import com.xiaodou.mission.vo.response.TaskListResponse.Task;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.mission.web.controller.TaskStatusResponse.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月29日
 * @description 任务状态响应类
 * @version 1.0
 */
public class TaskStatusResponse extends ResultInfo {

  public TaskStatusResponse(ResultType type) {
    super(type);
  }

  /** task 任务详情 */
  private Task task;

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }

}
