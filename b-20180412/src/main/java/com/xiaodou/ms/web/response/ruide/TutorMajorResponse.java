package com.xiaodou.ms.web.response.ruide;

import com.xiaodou.ms.model.ruide.TutorMajorModel;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

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


