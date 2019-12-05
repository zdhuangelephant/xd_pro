package com.xiaodou.dashboard.vo.crontmonitor.request;

import lombok.Data;

/**
 * @name @see
 *       com.xiaodou.dashboard.vo.crontmonitor.request.MonitorApiListRequest
 *       .java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月19日
 * @description 监控点列表请求类
 * @version 1.0
 */
@Data
public class MonitorApiListRequest {
	/** pageNo 请求页码 */
	private Integer pageNo = 1;
	/** pageSize 分页记录数量 */
	private Integer pageSize = 20;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
}