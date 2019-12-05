package com.xiaodou.ms.web.request.sms;

import com.xiaodou.ms.web.request.BaseRequest;

public class TemplateTypeCreateRequest extends BaseRequest {
	
	private Integer id;
	private String name;
	private String description;
	private Integer retryTime;
	private Integer cacheTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getRetryTime() {
		return retryTime;
	}
	public void setRetryTime(Integer retryTime) {
		this.retryTime = retryTime;
	}
	public Integer getCacheTime() {
		return cacheTime;
	}
	public void setCacheTime(Integer cacheTime) {
		this.cacheTime = cacheTime;
	}

}
