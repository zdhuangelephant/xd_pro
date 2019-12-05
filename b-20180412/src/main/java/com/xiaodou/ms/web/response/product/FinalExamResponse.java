package com.xiaodou.ms.web.response.product;

import com.xiaodou.ms.model.product.FinalExamModel;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FinalExamResponse extends BaseResponse {
	public FinalExamResponse(ResultType resultType) {
		super(resultType);
	}

	public FinalExamResponse() {
	}
	private FinalExamModel finalExamModel;
}