package com.xiaodou.logmonitor.domain;

import java.sql.Timestamp;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@CollectionName("common_model")
public class CommonModel extends MongoBaseModel {
  @Pk
  private String actionId;// 主键ID
  private Timestamp logTime;// 日志时间



  //日志配置顺序
  private String sLogTime;// 日志时间(展示用)
  private String clientIp;// 远程访问IP
  private String traceId;// 日志跟踪ID
  private String traceNumber;// 日志跟踪顺序
  private String projectName;// 程序名称
  private String excutePoint;// 执行点
  private String serverName;// 主机名
  private String localIp;// 主机IP
  private String localMac;// 主机MAC
  private String logName;// 日志名称
  private Boolean hasError;// 存在异常
  private String errorMessage;// 　异常描述
  private String actionModel;// 行为属性
  private String standby;//备用字段
  private String useTime;//用时
  public CommonModel() {}
  
  public CommonModel(String localIp, String localMac, String projectName) {
    this.localIp = localIp;
    this.localMac = localMac;
    this.projectName = projectName;
  }

  public String getActionId() {
    return actionId;
  }

  public void setActionId(String actionId) {
    this.actionId = actionId;
  }

  public Timestamp getLogTime() {
    return logTime;
  }

  public void setLogTime(Timestamp logTime) {
    this.logTime = logTime;
  }

  public String getsLogTime() {
    return sLogTime;
  }

  public void setsLogTime(String sLogTime) {
    this.sLogTime = sLogTime;
  }

  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public String getActionModel() {
    return actionModel;
  }

  public void setActionModel(String actionModel) {
    this.actionModel = actionModel;
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

  public String getTraceNumber() {
    return traceNumber;
  }

  public void setTraceNumber(String traceNumber) {
    this.traceNumber = traceNumber;
  }

  public String getTraceId() {
    return traceId;
  }

  public void setTraceId(String traceId) {
    this.traceId = traceId;
  }

  public String getStandby() {
    return standby;
  }

  public void setStandby(String standby) {
    this.standby = standby;
  }


public String getUseTime() {
	return useTime;
}

public void setUseTime(String useTime) {
	this.useTime = useTime;
}
}
