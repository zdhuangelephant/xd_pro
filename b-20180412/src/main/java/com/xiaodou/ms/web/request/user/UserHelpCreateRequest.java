package com.xiaodou.ms.web.request.user;

import com.xiaodou.ms.web.request.BaseRequest;

public class UserHelpCreateRequest extends BaseRequest {
	
	private Integer id;
	private String title;
	private String content;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
