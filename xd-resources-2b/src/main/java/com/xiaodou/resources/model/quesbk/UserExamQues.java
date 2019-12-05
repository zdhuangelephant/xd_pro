package com.xiaodou.resources.model.quesbk;

import com.xiaodou.resources.model.BaseEntity;


public class UserExamQues extends BaseEntity {

	private QuesbkQues quesDetail;

	private String myAnswer;

	public String getMyAnswer() {
		return myAnswer;
	}

	public void setMyAnswer(String myAnswer) {
		this.myAnswer = myAnswer;
	}

	public QuesbkQues getQuesDetail() {
		return quesDetail;
	}

	public void setQuesDetail(QuesbkQues quesDetail) {
		this.quesDetail = quesDetail;
	}

}
