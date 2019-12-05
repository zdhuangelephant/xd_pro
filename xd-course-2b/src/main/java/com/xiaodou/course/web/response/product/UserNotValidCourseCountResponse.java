package com.xiaodou.course.web.response.product;

import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserNotValidCourseCountResponse extends BaseResponse {

	public UserNotValidCourseCountResponse() {
	}

	public UserNotValidCourseCountResponse(ResultType type) {
		super(type);
	}

	public UserNotValidCourseCountResponse(ProductResType resType) {
		super(resType);
	}

	public UserNotValidCourseCountResponse(UcenterResType resType) {
		setRetcode(resType.getCode());
		setRetdesc(resType.getMsg());
	}

	public String getOtherExamCourseCount() {
		return otherExamCourseCount;
	}

	public void setOtherExamCourseCount(String otherExamCourseCount) {
		this.otherExamCourseCount = otherExamCourseCount;
	}

	private String otherExamCourseCount = String.valueOf(0);// 其它考期课程数目
}
