package com.xiaodou.common.util.log.model;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.Log4jProp;

/**
 * @name @see com.xiaodou.common.util.log.model.ActionEntity.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月24日
 * @description 日志记录行为参数类
 * @version 1.0
 */
public class ActionEntity {

  /** projectName 项目名称 */
  private static final String projectName = Log4jProp.getParams("projectName");
  /** excutePoint 执行点 */
  private String excutePoint;
  /** serverName 节点名 */
  private static final String serverName = CommUtil.getServerName();
  /** ipAddr IP地址 */
  private static final String ipAddr = CommUtil.getLocalIP();
  /** macAddr MAC地址 */
  private static final String macAddr = CommUtil.getMACAddress();
  /** logName 日志类型 */
  private String logName;
  /** logName 业务ID */
  private String traceId;
  /** logName 业务号 */
  private String traceNumber;
  /** standBy 拓展字段 */
  private String standBy;
  /** actionInfo 行为信息 */
  private Object actionInfo;
  /** hasError 是否有异常 */
  private Boolean hasError;
  /** errorMessage 异常信息 */
  private String errorMessage;
  /** errorMessage 请求用时 */ 
  private String useTime;
  public String getExcutePoint() {
    return excutePoint;
  }

  public void setExcutePoint(String excutePoint) {
    this.excutePoint = excutePoint;
  }

  public String getLogName() {
    return logName;
  }

  public void setLogName(String logName) {
    this.logName = logName;
  }

  public String getStandBy() {
    return standBy;
  }

  public void setStandBy(String standBy) {
    this.standBy = standBy;
  }

  public Object getActionInfo() {
    return actionInfo;
  }

  public void setActionInfo(Object actionInfo) {
    this.actionInfo = actionInfo;
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
  public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
  @Override
  public String toString() {
    return String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s",traceId,traceNumber,projectName, excutePoint, serverName,
        ipAddr, macAddr, logName,hasError, errorMessage, actionInfo, standBy,useTime);
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



}
