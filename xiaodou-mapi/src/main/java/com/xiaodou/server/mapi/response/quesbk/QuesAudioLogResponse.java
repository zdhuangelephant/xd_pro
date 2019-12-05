package com.xiaodou.server.mapi.response.quesbk;

import com.xiaodou.server.mapi.domain.QuesbkAudioLogVo;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.vo.response.DailyExamResponse.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年11月09日
 * @description 答题音频日志
 * @version 1.0
 */
public class QuesAudioLogResponse extends BaseResponse {
	public QuesAudioLogResponse(){}
	public QuesAudioLogResponse(ResultType type) {
		super(type);
	}

	public QuesbkAudioLogVo getLog() {
		return log;
	}

	public void setLog(QuesbkAudioLogVo log) {
		this.log = log;
	}

	private QuesbkAudioLogVo log = new QuesbkAudioLogVo();

}
