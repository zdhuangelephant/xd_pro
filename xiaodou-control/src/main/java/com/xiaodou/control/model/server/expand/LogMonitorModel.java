package com.xiaodou.control.model.server.expand;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.control.constant.Constant;
import com.xiaodou.control.enums.StartupConfigCommand;
import com.xiaodou.control.vo.ConfigInfoVo;
import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;
@CollectionName("LogMonitor")
public class LogMonitorModel extends MongoBaseModel{
	public LogMonitorModel() {
	}

	public LogMonitorModel(ConfigInfoVo configInfo) {
		if (null == configInfo
				|| StringUtils.isAllBlank(configInfo.getId(),
						configInfo.getCommandInfo()))
			return;
		this.setLogMonitorId(configInfo.getId());
		String[] info = configInfo.getCommandInfo().split("#LOGMONITOR#");
		if (info.length < 2)
			return;
		this.logPath = info[0]; 
		this.logPrefix = info[1];
	}

	/** id 主键ID */
	@Pk
	private String logMonitorId;
	/** logPath 待收集日志路径 */
	private String logPath;
	/** logPrefix 路径匹配前缀 */
	private String logPrefix;


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

	public String getLogMonitorId() {
		return logMonitorId;
	}

	public void setLogMonitorId(String logMonitorId) {
		this.logMonitorId = logMonitorId;
	}

}
