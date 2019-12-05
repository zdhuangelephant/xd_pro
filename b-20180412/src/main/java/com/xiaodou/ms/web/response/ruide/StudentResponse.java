package com.xiaodou.ms.web.response.ruide;

import com.xiaodou.ms.model.ruide.StudentModel;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author zdh:
* @date 2017年6月7日
*
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentResponse extends BaseResponse {
	public StudentResponse(ResultType resultType) {
		super(resultType);
	}

	public StudentResponse() {
	}
	private StudentModel studentModel;
}