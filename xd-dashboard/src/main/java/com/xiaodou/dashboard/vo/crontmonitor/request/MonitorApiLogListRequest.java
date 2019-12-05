package com.xiaodou.dashboard.vo.crontmonitor.request;

import lombok.Data;

/**
 * @name @see
 *       com.xiaodou.dashboard.vo.crontmonitor.request.MonitorApiLogListRequest
 *       .java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月19日
 * @description 监控点调用日志列表请求类
 * @version 1.0
 */
@Data
public class MonitorApiLogListRequest {
	/** apiId 监控点ID */
	private String apiId;
	/** pageNo 请求页码 */
	private Integer pageNo = 1;
	/** pageSize 分页记录数量 */
	private Integer pageSize = 20;

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
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