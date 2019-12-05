package com.xiaodou.vo.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.vo.request.QuesAudioLogDetailPojo.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 20171109
 * @description 答题音频日志request
 * @version 1.0
 */
public class QuesAudioLogDetailPojo extends QuesBasePojo {
	// 题目Id
	  @NotEmpty
	private String quesId;

	public String getQuesId() {
		return quesId;
	}

	public void setQuesId(String quesId) {
		this.quesId = quesId;
	}
	



}
