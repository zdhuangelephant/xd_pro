package com.xiaodou.server.mapi.request.quesbk;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 20171109
 * @description 答题音频日志request
 * @version 1.0
 */
public class QuesAudioLogDetailPojo extends MapiBaseRequest {
	  @NotEmpty
	private String quesLogId;

	public String getQuesLogId() {
		return quesLogId;
	}

	public void setQuesLogId(String quesLogId) {
		this.quesLogId = quesLogId;
	}
	



}
