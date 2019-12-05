package com.xiaodou.ms.web.response.ruide;

import com.xiaodou.ms.model.ruide.MajorCategoryModel;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author zdh:
* @date 2017年6月13日
*
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class MajorCategoryResponse  extends BaseResponse {
	public MajorCategoryResponse(ResultType resultType) {
		super(resultType);
	}

	public MajorCategoryResponse() {
	}
	private MajorCategoryModel majorCategoryModel;
}
