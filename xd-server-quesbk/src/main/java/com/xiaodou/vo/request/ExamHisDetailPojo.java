package com.xiaodou.vo.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class ExamHisDetailPojo extends QuesBasePojo {
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
