package com.xiaodou.dashboard.vo.log.response;

import com.xiaodou.dashboard.model.alarm.domain.AlarmEventDo;
import com.xiaodou.dashboard.model.log.ProjectModel;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.dashboard.vo.log.response.ProjectResponse.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月6日
 * @description 工程响应类
 * @version 1.0
 */
public class ProjectResponse extends ResultInfo {

  /** project 工程模型 */
  private ProjectModel project;
  /** event 报警事件 */
  private AlarmEventDo event;

  public ProjectResponse() {}

  public ProjectResponse(ResultType resultType) {
    super(resultType);
  }

  public ProjectModel getProject() {
    return project;
  }

  public void setProject(ProjectModel project) {
    this.project = project;
  }

  public AlarmEventDo getEvent() {
    return event;
  }

  public void setEvent(AlarmEventDo event) {
    this.event = event;
  }

}
