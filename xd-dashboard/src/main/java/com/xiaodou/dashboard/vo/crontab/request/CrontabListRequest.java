package com.xiaodou.dashboard.vo.crontab.request;

import lombok.Data;

/**
 * @name @see com.xiaodou.dashboard.vo.crontab.request.CrontabListRequest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月5日
 * @description 定时任务列表请求类
 * @version 1.0
 */
@Data
public class CrontabListRequest {
	/** businessCode 业务码值 */
	private String businessCode;
	/** pageNo 请求页码 */
	private Integer pageNo = 1;
	/** pageSize 分页记录数量 */
	private Integer pageSize = 20;

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
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