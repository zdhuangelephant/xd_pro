package com.xiaodou.logmonitor.domain;

import java.sql.Timestamp;
import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

/**
 * @name @see com.xiaodou.common.util.log.model.TraceEntity.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2018年4月24日
 * @description 业务流程日志类
 * @version 1.0
 */
@CollectionName("trace_model")
public class TraceModel extends MongoBaseModel {
	@Pk
	private String actionId;// 主键ID
	private Timestamp logTime;// 日志时间

	// 日志配置顺序
	private String sLogTime;// 日志时间(展示用)[0]
	private String clientIp;// 远程访问IP[1]
	private String projectName;// 程序名称[2]
	private String excutePoint;// 执行点[3]
	private String serverName;// 主机名[4]
	private String localIp;// 主机IP[5]
	private String localMac;// 主机MAC[6]
	private String logName;// 日志名称[7]
	
	/** standBy 拓展字段 */
	private String standBy;// 备用字段[8]
	/** traceId 会话标识符 */
	private String traceId;// 业务流唯一ID[9]
	/** projectId 程序标识符 */
	private String projectId;// 程序ID[10]
	/** processId 进程标识符 */
	private String processId;// 进程标识符[11]
	/** executionTime 执行时间 */
	private String executionTime;// 方法执行时间[12]
	/** personalityData 个性化数据 */
	private String personalityData;// 个性化数据[13]
	/** personalityData 父类ID */
	private String parentId; // 链路父类ID[14]
	/** personalityData 当前ID */
	private String myId; // 链路自己ID[15]
	/** 入参 */
	private String entryParameter;// 入参[16]
	/** 执行结果 */
	private String excuteResult;// 方法执行结果[17]
	/** 错误信息 */
	private String errorMsg;
	
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
	public String getLocalIp() {
		return localIp;
	}
	public void setLocalIp(String localIp) {
		this.localIp = localIp;
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
