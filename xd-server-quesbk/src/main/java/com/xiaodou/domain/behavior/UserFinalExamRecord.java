package com.xiaodou.domain.behavior;

import com.xiaodou.domain.BaseEntity;

public class UserFinalExamRecord  extends BaseEntity {
	private String id;

	private String finalExamId;

	private String paperNo;

	private String userId;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFinalExamId() {
		return finalExamId;
	}

	public void setFinalExamId(String finalExamId) {
		this.finalExamId = finalExamId;
	}

	public String getPaperNo() {
		return paperNo;
	}

	public void setPaperNo(String paperNo) {
		this.paperNo = paperNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}
