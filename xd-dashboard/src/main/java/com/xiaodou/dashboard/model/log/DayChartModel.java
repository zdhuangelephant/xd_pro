package com.xiaodou.dashboard.model.log;

import com.xiaodou.common.annotation.GeneralField;
public class DayChartModel {
	@GeneralField
	private String timeInterval;//主键ID
	@GeneralField
	private String logCount;//远程访问IP
	public String getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}
	public String getLogCount() {
		return logCount;
	}
	public void setLogCount(String logCount) {
		this.logCount = logCount;
	}

}
