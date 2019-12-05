package com.xiaodou.ms.web.request.product;

import com.xiaodou.ms.web.request.BaseRequest;

/**
 * Created by zhouhuan .
 */
public class ProductTreeRequest extends BaseRequest {

    private String id;
    private String parentId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
