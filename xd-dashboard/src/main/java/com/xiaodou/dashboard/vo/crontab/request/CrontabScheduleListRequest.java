package com.xiaodou.dashboard.vo.crontab.request;

import lombok.Data;

/**
 * @name @see
 *       com.xiaodou.dashboard.vo.crontab.request.CrontabScheduleListRequest
 *       .java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月10日
 * @description 调度记录列表请求
 * @version 1.0
 */
@Data
public class CrontabScheduleListRequest {

	/** configId 任务ID */
	private String configId;
	/** pageNo 请求页码 */
	private Integer pageNo = 1;
	/** pageSize 分页记录数量 */
	private Integer pageSize = 20;

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
