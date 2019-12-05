package com.xiaodou.ms.web.response.config;

import com.xiaodou.common.info.model.CommonInfo;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = true)
public class ConfInfoResponse extends BaseResponse {
	public ConfInfoResponse(ResultType resultType) {
		super(resultType);
	}

	public ConfInfoResponse() {
	}
	private CommonInfo confInfo;
}
