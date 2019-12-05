package com.xiaodou.server.mapi.request.product;

import com.xiaodou.server.mapi.request.MapiBaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
/* 最后一个id */
public class UserNotValidCourseRequest extends MapiBaseRequest {
	private String upperId;

	public String getUpperId() {
		return upperId;
	}

	public void setUpperId(String upperId) {
		this.upperId = upperId;
	}

}
