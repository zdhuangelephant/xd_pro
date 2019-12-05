package com.xiaodou.dashboard.vo.crontab.response;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.dashboard.model.crontab.CrontabJobLog;

/**
 * @name @see com.xiaodou.dashboard.vo.crontab.response.CrontabJobLogModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月10日
 * @description 任务执行记录展示模型
 * @version 1.0
 */
public class CrontabJobLogModel {

	/** excutorId 执行ID */
	private String excutorId;
	/** logList 执行记录列表 */
	private List<CrontabJobLog> logList = Lists.newArrayList();
	/** size 数量 */
	private Integer size;
	/** finalCrontTime 最后调度时间 */
	private Integer finalDataVersion;

	public void pushJob(CrontabJobLog log) {
		this.logList.add(log);
		this.size = logList.size();
		if (null == log.getCrontTime())
			return;
		if (null == this.finalDataVersion
				|| finalDataVersion < log.getDataVersion())
			this.finalDataVersion = log.getDataVersion();
		Collections.sort(logList, new Comparator<CrontabJobLog>() {
			@Override
			public int compare(CrontabJobLog o1, CrontabJobLog o2) {
				return o2.getDataVersion().compareTo(o1.getDataVersion());
			}
		});
	}

	public String getExcutorId() {
		return excutorId;
	}

	public void setExcutorId(String excutorId) {
		this.excutorId = excutorId;
	}

	public List<CrontabJobLog> getLogList() {
		return logList;
	}

	public Integer getSize() {
		return size;
	}

	public Integer getFinalDataVersion() {
		return finalDataVersion;
	}

}
