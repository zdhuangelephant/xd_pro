package com.xiaodou.ms.web.response.ruide;

import com.xiaodou.ms.model.ruide.ActivityModel;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

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