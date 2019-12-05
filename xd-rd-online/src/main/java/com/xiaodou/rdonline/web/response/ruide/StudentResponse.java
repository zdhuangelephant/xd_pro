package com.xiaodou.rdonline.web.response.ruide;

import com.xiaodou.rdonline.domain.student.StudentModel;
import com.xiaodou.rdonline.web.response.BaseResponse;
import com.xiaodou.rdonline.web.response.ResultType;

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