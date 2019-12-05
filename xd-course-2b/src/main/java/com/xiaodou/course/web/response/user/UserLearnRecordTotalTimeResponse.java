package com.xiaodou.course.web.response.user;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserLearnRecordTotalTimeResponse extends BaseResponse {

	public UserLearnRecordTotalTimeResponse(ResultType resultType) {
		super(resultType);
	}

	public UserLearnRecordTotalTimeResponse(ProductResType resultType) {
		super(resultType);
	}

	public String getLearnTime() {
		return learnTime;
	}

	public void setLearnTime(String learnTime) {
		this.learnTime = learnTime;
	}

	/* 总学习时长 */
	private String learnTime = StringUtils.EMPTY;
}
