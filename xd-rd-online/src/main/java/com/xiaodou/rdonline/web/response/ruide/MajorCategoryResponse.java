package com.xiaodou.rdonline.web.response.ruide;

import com.xiaodou.rdonline.domain.activity.ActivityModel;
import com.xiaodou.rdonline.domain.majorCategory.MajorCategoryModel;
import com.xiaodou.rdonline.web.response.BaseResponse;
import com.xiaodou.rdonline.web.response.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author zdh:
* @date 2017年6月13日
*
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class MajorCategoryResponse extends BaseResponse {
	public MajorCategoryResponse(ResultType resultType) {
		super(resultType);
	}

	public MajorCategoryResponse() {
	}
	private MajorCategoryModel majorCategoryModel;
}
