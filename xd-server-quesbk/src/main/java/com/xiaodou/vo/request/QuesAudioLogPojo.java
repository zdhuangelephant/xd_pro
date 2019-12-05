package com.xiaodou.vo.request;

import java.sql.Timestamp;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.vo.request.QuesAudioLogPojo.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 20171109
 * @description 答题音频日志request
 * @version 1.0
 */
public class QuesAudioLogPojo extends QuesBasePojo {
	// 题目Id
	  @NotEmpty
	private String quesId;
	// 题目答案音频url
	private String quesVideoUrl;
	
	public String getQuesId() {
		return quesId;
	}

	public void setQuesId(String quesId) {
		this.quesId = quesId;
	}

	public String getQuesVideoUrl() {
		return quesVideoUrl;
	}

	public void setQuesVideoUrl(String quesVideoUrl) {
		this.quesVideoUrl = quesVideoUrl;
	}

}
