package com.xiaodou.dashboard.model.log;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;

@CollectionName("common_model")
public class ActionModel {
  @Pk
  private String actionId;// 主键ID
  private String sLogTime;// 日志时间(展示用)
  private String localIp;// 主机IP
  private String projectName;// 程序名称
  private String excutePoint;// 执行点
  private String excutePointShortName;// 执行点简称
  private String serverName;// 主机名
  private String localMac;// 主机MAC
  private String logName;// 日志名称
  private String traceId;// 日志跟踪ID
  private String traceNumber;// 日志跟踪顺序
  private String clientIp;// 远程访问IP
  private Boolean hasError;// 存在异常
  private String errorMessage;// 　异常描述
  private String actionModel;// 行为属性
  private String standby;

  public String getActionId() {
    return actionId;
  }

  public void setActionId(String actionId) {
    this.actionId = actionId;
  }

  public String getsLogTime() {
    return sLogTime;
  }

  public void setsLogTime(String sLogTime) {
    this.sLogTime = sLogTime;
  }

  public String getLocalIp() {
    return localIp;
  }

  public void setLocalIp(String localIp) {
    this.localIp = localIp;
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

  public String getExcutePointShortName() {
    return excutePointShortName;
  }

  public void setExcutePointShortName(String excutePointShortName) {
    this.excutePointShortName = excutePointShortName;
  }

  public String getServerName() {
    return serverName;
  }

  public void setServerName(String serverName) {
    this.serverName = serverName;
  }

  public String getLocalMac() {
    return localMac;
  }

  public void setLocalMac(String localMac) {
    this.localMac = localMac;
  }

  public String getLogName() {
    return logName;
  }

  public void setLogName(String logName) {
    this.logName = logName;
  }

  public String getTraceId() {
    return traceId;
  }

  public void setTraceId(String traceId) {
    this.traceId = traceId;
  }

  public String getTraceNumber() {
    return traceNumber;
  }

  public void setTraceNumber(String traceNumber) {
    this.traceNumber = traceNumber;
  }

  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public Boolean getHasError() {
    return hasError;
  }

  public void setHasError(Boolean hasError) {
    this.hasError = hasError;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getActionModel() {
    return actionModel;
  }

  public void setActionModel(String actionModel) {
    this.actionModel = actionModel;
  }

  public String getStandby() {
    return standby;
  }

  public void setStandby(String standby) {
    this.standby = standby;
  }

}
