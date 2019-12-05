package com.xiaodou.userCenter.request.selftaught;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.userCenter.request.BaseRequest;

public class UserPraiseRequest extends BaseRequest{

	/**
	 * 目标用户ID //非必传 为空则返回当前用户信息
	 */
	@NotEmpty
	private String targetUserId;

	public UserPraiseRequest(){}
	
	public String getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}
	
}
