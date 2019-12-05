package com.xiaodou.dashboard.model.log;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;

@CollectionName("project_model")
public class ProjectModel  {
  /** id 主键ID */
  @Pk
  private String projectId;
  /** projectName 项目名称 */
  private String projectName;
  /** excutePoint 执行点 */
  private String excutePoint;
  /** alarmEventId 报警事件ID */
  private String alarmEventId;

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getExcutePoint() {
    return excutePoint;
  }

  public void setExcutePoint(String excutePoint) {
    this.excutePoint = excutePoint;
  }

  public String getAlarmEventId() {
    return alarmEventId;
  }

  public void setAlarmEventId(String alarmEventId) {
    this.alarmEventId = alarmEventId;
  }

}
