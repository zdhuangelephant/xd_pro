package com.xiaodou.vo.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.constant.ResultType;
import com.xiaodou.vo.response.WrongQuesListResponse.ChapterInfo;

public class StoreQuesListResponse extends BaseResponse {

	public StoreQuesListResponse(ResultType type) {
		super(type);
	}

	/**
	 * 章列表
	 */
	private List<ChapterInfo> courseItemList = Lists.newArrayList();

	public List<ChapterInfo> getCourseItemList() {
		return courseItemList;
	}

	public void setCourseItemList(List<ChapterInfo> courseItemList) {
		this.courseItemList = courseItemList;
	}
}
