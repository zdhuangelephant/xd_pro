package com.xiaodou.course.web.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.course.vo.product.StCourseInfo;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @name UserExpCourseResponse CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年11月10日
 * @description 用戶过期课程
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserNotValidCourseResponse extends BaseResponse {

	public UserNotValidCourseResponse() {
	}

	public UserNotValidCourseResponse(ResultType type) {
		super(type);
	}

	public UserNotValidCourseResponse(ProductResType resType) {
		super(resType);
	}

	public UserNotValidCourseResponse(UcenterResType resType) {
		setRetcode(resType.getCode());
		setRetdesc(resType.getMsg());
	}

	private List<StCourseInfo> otherExamCourseList = Lists.newArrayList();// 其它考期课程列表

	public List<StCourseInfo> getOtherExamCourseList() {
		return otherExamCourseList;
	}

	public void setOtherExamCourseList(List<StCourseInfo> otherExamCourseList) {
		this.otherExamCourseList = otherExamCourseList;
	}

}
