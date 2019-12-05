package com.xiaodou.dashboard.vo.log.request;

import lombok.Data;

/**
 * @name @see com.xiaodou.dashboard.vo.log.request.ActionRequest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月16日
 * @description 日志列表请求类
 * @version 1.0
 */
@Data
public class ActionRequest {
	/** id 主键ID */
	private String projectId;
	/** projectName 项目名称 */
	private String projectName;
	/** excutePoint 执行点 */
	private String excutePoint;
	/** serverName 服务名 */
	private String serverName;
	/** hasError 异常情况 */
	private Boolean hasError;
	/** lowerTime 起始时间 */
	private String lowerTime;
	/** upperTime 截止时间 */
	private String upperTime;
	/** page 页码 */
	private Integer page = 1;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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

	public Boolean getHasError() {
		return hasError;
	}

	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}

	public String getLowerTime() {
		return lowerTime;
	}

	public void setLowerTime(String lowerTime) {
		this.lowerTime = lowerTime;
	}

	public String getUpperTime() {
		return upperTime;
	}

	public void setUpperTime(String upperTime) {
		this.upperTime = upperTime;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
