package com.xiaodou.logmonitor.alarm;

import java.util.List;

import com.google.common.collect.Lists;
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
 * @description OutIn整体日志报警
 * @version 1.0
 */
public class OutInTotalAlarmRecord extends AlarmEntityImpl {

  /** projectName 程序名 */
  private String projectName;
  /** excutePoint 执行点 */
  private String excutePoint;
  /** failPercent 失败率 */
  private double failPercent = 0;
  /** alarmDetail 分布详情 */
  private List<PartDistribution> alarmDetail = Lists.newArrayList();

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

  public double getFailPercent() {
    return failPercent;
  }

  public void setFailPercent(double failPercent) {
    this.failPercent = failPercent;
  }

  public void pushPartDistribution(String serverName, Double failPercent) {
    this.alarmDetail.add(new PartDistribution(serverName, failPercent));
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
    StringBuilder build = new StringBuilder(200);
    for (PartDistribution part : alarmDetail) {
      build.append(String.format("[节点:%s,异常率%s]", part.getServerName(), part.getFailPercent()));
    }
    return String.format(AlarmRecordProp.getParams("alarm.outin.total.mailInfo"), projectName,
        excutePoint, Constant.OUTIN_THRESHOLD_PERCENT, failPercent, build.toString());
  }

  @Override
  public String getMessageInfo() {
    return String.format(AlarmRecordProp.getParams("alarm.outin.total.messageInfo"), projectName,
        excutePoint, Constant.OUTIN_THRESHOLD_PERCENT, failPercent);
  }

  /**
   * @name @see com.xiaodou.alarm.OutInTotalAlarmRecord.java
   * @CopyRright (c) 2017 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年5月25日
   * @description 节点分布情况
   * @version 1.0
   */
  public static class PartDistribution {
    /** serverName 节点名 */
    private String serverName;
    /** failPercent 失败率 */
    private Double failPercent;

    public PartDistribution(String serverName, Double failPercent) {
      this.serverName = serverName;
      this.failPercent = failPercent;
    }

    public String getServerName() {
      return serverName;
    }

    public void setServerName(String serverName) {
      this.serverName = serverName;
    }

    public Double getFailPercent() {
      return failPercent;
    }

    public void setFailPercent(Double failPercent) {
      this.failPercent = failPercent;
    }

  }
}
