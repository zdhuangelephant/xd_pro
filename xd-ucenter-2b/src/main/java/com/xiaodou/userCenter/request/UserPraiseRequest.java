package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class UserPraiseRequest extends BaseRequest{

	/**
	 * 目标用户ID 
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
