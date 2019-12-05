package com.xiaodou.logmonitor.domain;

import java.sql.Timestamp;

import com.xiaodou.common.util.log.model.OutInEntity;
import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;


@CollectionName("out_in_model")
public class OutInModel extends CommonModel {
  @Pk
  private String outinId;// 主键ID
  private Timestamp logTime;// 日志时间
  private String sLogTime;// 日志时间(展示用)
  private String localIp;// 主机IP
  private String projectName;// 程序名称
  private String excutePoint;// 执行点
  private String serverName;// 主机名
  private String localMac;// 主机MAC
  private String logName;// 日志名称
  private String traceId;// 日志跟踪ID
  private String traceNumber;// 日志跟踪顺序
  private OutInEntity outInModel;// 访问日志
  private String standby;
  private Boolean hasError;
  private String errorMessage;
  public String getOutinId() {
    return outinId;
  }

  public void setOutinId(String outinId) {
    this.outinId = outinId;
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

  public OutInEntity getOutInModel() {
    return outInModel;
  }

  public void setOutInModel(OutInEntity outInModel) {
    this.outInModel = outInModel;
  }

  public String getStandby() {
    return standby;
  }

  public void setStandby(String standby) {
    this.standby = standby;
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

}
