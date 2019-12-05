package com.xiaodou.dashboard.model.crontab;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;

/**
 * @name @see com.xiaodou.dashboard.model.crontab.CrontabJobLog.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月3日
 * @description 任务执行记录模型类
 * @version 1.0
 */
@Data
public class CrontabJobLog {
	/** id 主键ID */
	@Column(isMajor = true, autoIncrement = true)
	private Integer id;
	/** configId 任务ID */
	@Column(canUpdate = false)
	private Integer configId;
	/** contextId 执行者ID */
	@Column(canUpdate = false)
	private String contextId;
	/** excutorId 执行ID */
	@Column(canUpdate = false)
	private String excutorId;
	/** contextName 执行者节点名 */
	@Column(canUpdate = false)
	private String contextName;
	/** dataVersion 数据版本号 */
	@Column(canUpdate = false)
	private Integer dataVersion;
	/** crontStatus 调度状态 */
	private Short crontStatus;
	/** crontTime 调度时间 */
	@Column(betweenScope = true)
	private Timestamp crontTime;
	/** crontCost 调度耗时 */
	private Integer crontCost;
	/** crontResult 调度结果 */
	private String crontResult;
	/** crontRetry 重试次数 */
	private Short crontRetry;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public String getExcutorId() {
		return excutorId;
	}

	public void setExcutorId(String excutorId) {
		this.excutorId = excutorId;
	}

	public String getContextName() {
		return contextName;
	}

	public void setContextName(String contextName) {
		this.contextName = contextName;
	}

	public Integer getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(Integer dataVersion) {
		this.dataVersion = dataVersion;
	}

	public Short getCrontStatus() {
		return crontStatus;
	}

	public void setCrontStatus(Short crontStatus) {
		this.crontStatus = crontStatus;
	}

	public Timestamp getCrontTime() {
		return crontTime;
	}

	public void setCrontTime(Timestamp crontTime) {
		this.crontTime = crontTime;
	}

	public Integer getCrontCost() {
		return crontCost;
	}

	public void setCrontCost(Integer crontCost) {
		this.crontCost = crontCost;
	}

	public String getCrontResult() {
		return crontResult;
	}

	public void setCrontResult(String crontResult) {
		this.crontResult = crontResult;
	}

	public Short getCrontRetry() {
		return crontRetry;
	}

	public void setCrontRetry(Short crontRetry) {
		this.crontRetry = crontRetry;
	}

}
