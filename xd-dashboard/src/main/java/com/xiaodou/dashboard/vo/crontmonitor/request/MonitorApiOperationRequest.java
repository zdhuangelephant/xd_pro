package com.xiaodou.dashboard.vo.crontmonitor.request;

import lombok.Data;

import com.xiaodou.dashboard.model.crontmonitor.MonitorApi;

/**
 * @name @see
 *       com.xiaodou.dashboard.vo.crontmonitor.request.MonitorApiOperationRequest
 *       .java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月19日
 * @description 监控点操作请求类
 * @version 1.0
 */
@Data
public class MonitorApiOperationRequest {
	/** id 主键ID */
	private String id;
	/** name 名称 */
	private String name;
	/** protocol 协议类型 */
	private String protocol;
	/** format 数据传输格式 */
	private String format;
	/** host 主机域名 */
	private String url;
	/** method 方法类型 */
	private String method;
	/** params 参数列表 */
	private String params;
	/** timeOut 超时时间 */
	private Integer timeOut;
	/** retryTime 重试次数 */
	private Integer retryTime;

	public MonitorApi buildModel() {
		MonitorApi api = new MonitorApi();
		api.setId(this.id);
		api.setName(this.name);
		api.setProtocol(this.protocol);
		api.setFormat(this.format);
		api.setUrl(this.url);
		api.setMethod(this.method);
		api.setParams(this.params);
		api.setTimeOut(this.timeOut);
		api.setRetryTime(this.retryTime);
		return api;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Integer getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}

	public Integer getRetryTime() {
		return retryTime;
	}

	public void setRetryTime(Integer retryTime) {
		this.retryTime = retryTime;
	}

}
