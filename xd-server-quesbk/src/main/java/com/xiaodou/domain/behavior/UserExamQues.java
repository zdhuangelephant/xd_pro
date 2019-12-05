package com.xiaodou.domain.behavior;

import com.xiaodou.domain.BaseEntity;
import com.xiaodou.domain.product.QuesbkQues;

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
