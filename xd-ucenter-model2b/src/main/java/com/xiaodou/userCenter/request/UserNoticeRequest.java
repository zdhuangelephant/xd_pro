package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.enums.AnnotationType;

@AddValidField(annotiation = AnnotationType.NotEmpty, field = { "sessionToken" })
public class UserNoticeRequest extends BaseRequest {

	@NotEmpty
	private String noticeId;

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

}
