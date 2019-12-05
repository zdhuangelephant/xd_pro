package com.xiaodou.logmonitor.alarm;

import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.IAlarmEntity;
import com.xiaodou.logmonitor.constant.Constant;
import com.xiaodou.logmonitor.prop.AlarmRecordProp;

/**
 * @name @see com.xiaodou.alarm.OutInAlarmRecord.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月24日
 * @description OutIn部分日志报警
 * @version 1.0
 */
public class OverTimePartAlarmRecord extends AlarmEntityImpl {

  /** projectName 程序名 */
  private String projectName;
  /** excutePoint 执行点 */
  private String excutePoint;
  /** serverName 节点信息 */
  private String serverName;
  /** overTimePercent 超时率 */
  private double overTimePercent = 0;

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

  public String getServerName() {
    return serverName;
  }

  public void setServerName(String serverName) {
    this.serverName = serverName;
  }

  @Override
  public String getEventModule() {
    return projectName;
  }

  @Override
  public String getEventName() {
    return excutePoint;
  }

  @Override
  public IAlarmEntity getLoggerEntity() {
    return null;
  }

  @Override
  public String getMailInfo() {
    return String.format(AlarmRecordProp.getParams("alarm.overtime.part.mailInfo"), projectName,
        excutePoint, serverName, Constant.OUTIN_THRESHOLD_PERCENT, overTimePercent);
  }

  @Override
  public String getMessageInfo() {
    return String.format(AlarmRecordProp.getParams("alarm.overtime.part.messageInfo"), projectName,
        excutePoint, serverName, Constant.OUTIN_THRESHOLD_PERCENT, overTimePercent);
  }

public double getOverTimePercent() {
	return overTimePercent;
}

public void setOverTimePercent(double overTimePercent) {
	this.overTimePercent = overTimePercent;
}

}
