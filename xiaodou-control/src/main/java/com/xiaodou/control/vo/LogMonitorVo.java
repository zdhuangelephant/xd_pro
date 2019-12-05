package com.xiaodou.control.vo;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.control.constant.Constant;
import com.xiaodou.control.enums.StartupConfigCommand;

public class LogMonitorVo {
	public LogMonitorVo() {
	}

	public LogMonitorVo(ConfigInfoVo configInfo) {
		if (null == configInfo
				|| StringUtils.isAllBlank(configInfo.getId(),
						configInfo.getCommandInfo()))
			return;
		this.id = configInfo.getId();
		String[] info = configInfo.getCommandInfo().split("#LOGMONITOR#");
		if (info.length < 2)
			return;
		this.logPath = info[0];
		this.logPrefix = info[1];
	}

	/** id 主键ID */
	private String id;
	/** logPath 待收集日志路径 */
	private String logPath;
	/** logPrefix 路径匹配前缀 */
	private String logPrefix;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getLogPrefix() {
		return logPrefix;
	}

	public void setLogPrefix(String logPrefix) {
		this.logPrefix = logPrefix;
	}

	private String toString0() {
		return String.format("%s#LOGMONITOR#%s", this.logPath, this.logPrefix);
	}

	public ConfigInfoVo transferModel() {
		ConfigInfoVo model = new ConfigInfoVo(StartupConfigCommand.LogMonitor);
		model.setCommandInfo(this.toString0());
		model.setState(Constant.MARK_INUSE);
		return model;
	}
}
