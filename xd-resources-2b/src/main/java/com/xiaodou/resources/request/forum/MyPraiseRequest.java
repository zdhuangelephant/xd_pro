package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;


/**
 * 我赞过的文章
 * @author zhouhuan
 */
public class MyPraiseRequest extends BaseRequest{	
	/** 最后一个资源ID */
	private String resourcesId;
	/** 显示个数 */
	private Integer size=20;
	
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getResourcesId() {
		return resourcesId;
	}
	public void setResourcesId(String resourcesId) {
		this.resourcesId = resourcesId;
	}
	
}
