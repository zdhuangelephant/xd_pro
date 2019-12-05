package com.xiaodou.vo.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.constant.ResultType;

/**
 * @name @see com.xiaodou.vo.response.DailyExamResponse.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年11月09日
 * @description 答题音频日志列表
 * @version 1.0
 */
public class QuesAudioLogListResponse extends BaseResponse {

	public QuesAudioLogListResponse(ResultType type) {
		super(type);
	}

	public List<QuesbkAudioLogVo> getList() {
		return list;
	}

	public void setList(List<QuesbkAudioLogVo> list) {
		this.list = list;
	}
	
	public Integer getLogCount() {
		return logCount;
	}

	public void setLogCount(Integer logCount) {
		this.logCount = logCount;
	}

	private Integer logCount=0;

	private List<QuesbkAudioLogVo> list = Lists.newArrayList();

}
