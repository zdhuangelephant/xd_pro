package com.xiaodou.course.web.request.product;

import com.xiaodou.course.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserNotValidCourseRequest extends BaseRequest {
	/* 最后一个id */
	private String upperId;

	public String getUpperId() {
		return upperId;
	}

	public void setUpperId(String upperId) {
		this.upperId = upperId;
	}
}
