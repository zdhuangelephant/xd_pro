package com.xiaodou.common.util.log.model;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.Log4jProp;

/**
 * @name @see com.xiaodou.common.util.log.model.TraceEntity.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2018年4月24日
 * @description 业务流程日志类
 * @version 1.0
 */
public class TraceEntity {

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
	/** standBy 拓展字段 */
	private String standBy;
	/** traceId 会话标识符 */
	private String traceId;
	/** projectId 程序标识符 */
	private String projectId;
	/** processId 进程标识符 */
	private String processId;
	/** executionTime 执行时间 */
	private String executionTime;
	/** personalityData 个性化数据 */
	private String personalityData;
	/** personalityData 父类ID */
	private String parentId;
	/** personalityData 当前ID */
	private String myId;
	/** 入参 */
	private String entryParameter;
	/** 执行结果 */
	private String excuteResult;
	
	private String errorMsg;
	
	/** personalityData 方法链路 */
    private List<String> functionList=Lists.newArrayList();

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

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
	}

	public String getPersonalityData() {
		return personalityData;
	}

	public void setPersonalityData(String personalityData) {
		this.personalityData = personalityData;
	}

	public static String getProjectname() {
		return projectName;
	}

	public static String getServername() {
		return serverName;
	}

	public static String getIpaddr() {
		return ipAddr;
	}

	public static String getMacaddr() {
		return macAddr;
	}

	@Override
	public String toString() {
		return String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s", projectName,
				excutePoint, serverName, ipAddr, macAddr, logName,standBy,traceId,
				projectId, processId,executionTime,personalityData,parentId,myId,entryParameter,excuteResult,errorMsg);
	}

	public List<String> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<String> functionList) {
		this.functionList = functionList;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMyId() {
		return myId;
	}

	public void setMyId(String myId) {
		this.myId = myId;
	}

	public String getEntryParameter() {
		return entryParameter;
	}

	public void setEntryParameter(String entryParameter) {
		this.entryParameter = entryParameter;
	}

	public String getExcuteResult() {
		return excuteResult;
	}

	public void setExcuteResult(String excuteResult) {
		this.excuteResult = excuteResult;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}



}
