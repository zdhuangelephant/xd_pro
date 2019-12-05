package com.xiaodou.resources.request.quesbk;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class ExamHisDetailPojo extends BaseRequest {
	/**
	 * 练习ID
	 */
	@NotEmpty
	private String examID;

	public String getExamID() {
		return examID;
	}

	public void setExamID(String examID) {
		this.examID = examID;
	}
}
