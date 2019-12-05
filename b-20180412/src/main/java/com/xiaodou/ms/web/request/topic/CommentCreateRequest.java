package com.xiaodou.ms.web.request.topic;

import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class CommentCreateRequest extends BaseRequest { 
	@NotEmpty
	private Integer id;

	//回帖状态
	private Integer status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
