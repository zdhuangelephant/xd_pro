package com.xiaodou.rdonline.web.response.ruide;


import com.xiaodou.rdonline.domain.activity.ActivityModel;
import com.xiaodou.rdonline.web.response.BaseResponse;
import com.xiaodou.rdonline.web.response.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityResponse extends BaseResponse {
	public ActivityResponse(ResultType resultType) {
		super(resultType);
	}

	public ActivityResponse() {
	}
	private ActivityModel activityModel;
}