package com.xiaodou.vo.response;

import com.xiaodou.constant.ResultType;

/**
 * @name @see com.xiaodou.vo.response.DailyExamResponse.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年11月09日
 * @description 答题音频日志数量
 * @version 1.0
 */
public class QuesAudioLogCountResponse extends BaseResponse {

	public QuesAudioLogCountResponse(ResultType type) {
		super(type);
	}

	public String getAudioCount() {
		return audioCount;
	}

	public void setAudioCount(String audioCount) {
		this.audioCount = audioCount;
	}

	private String audioCount="0";

}
