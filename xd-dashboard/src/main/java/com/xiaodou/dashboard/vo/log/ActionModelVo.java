package com.xiaodou.dashboard.vo.log;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ActionModelVo {
	private Timestamp logTime;// 日志时间
	private String projectName;// 程序名称
	private String excutePoint;// 执行点
	private String serverName;// 主机名
	private String logName;// 日志名称

	public Timestamp getLogTime() {
		return logTime;
	}

	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
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

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

}
