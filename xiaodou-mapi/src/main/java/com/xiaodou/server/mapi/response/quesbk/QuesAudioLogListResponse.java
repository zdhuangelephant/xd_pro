package com.xiaodou.server.mapi.response.quesbk;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.domain.QuesbkAudioLogVo;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

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
	public QuesAudioLogListResponse(){}
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

	private List<QuesbkAudioLogVo> list = Lists.newArrayList();
	private Integer logCount=0;
}
