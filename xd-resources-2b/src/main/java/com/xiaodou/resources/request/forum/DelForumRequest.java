package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.request.BaseRequest;


/**
 * 话题详情请求－话题部分
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午6:12:07
 */

public class DelForumRequest extends BaseRequest {

	/** 资源ID */
	private String resourcesId;

	public String getResourcesId() {
		return resourcesId;
	}

	public void setResourcesId(String resourcesId) {
		this.resourcesId = resourcesId;
	}

}
