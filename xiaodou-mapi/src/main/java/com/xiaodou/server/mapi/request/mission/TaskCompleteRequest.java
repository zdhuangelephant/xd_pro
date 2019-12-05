package com.xiaodou.server.mapi.request.mission;

import com.xiaodou.server.mapi.request.MapiBaseRequest;

/**
 * @name @see com.xiaodou.server.mapi.request.mission.TaskListRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月29日
 * @description 任务完成时间点
 * @version 1.0
 */
public class TaskCompleteRequest extends MapiBaseRequest {
  /** earliestDate 最早日期 */
  private String earliestDate;

  public String getEarliestDate() {
    return earliestDate;
  }

  public void setEarliestDate(String earliestDate) {
    this.earliestDate = earliestDate;
  }

}
