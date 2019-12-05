package com.xiaodou.rdonline.web.response.ruide;


import com.xiaodou.rdonline.domain.tutormajor.TutorMajorModel;
import com.xiaodou.rdonline.web.response.BaseResponse;
import com.xiaodou.rdonline.web.response.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TutorMajorResponse extends BaseResponse {
	public TutorMajorResponse(ResultType resultType) {
		super(resultType);
	}

	public TutorMajorResponse() {
	}
	private TutorMajorModel tutorMajorModel;
}


