package com.xiaodou.server.mapi.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

public class PraiseRequest extends MapiBaseRequest{


	/**
	 * 目标用户ID
	 *  
	 */
	@NotEmpty
	private String targetUserId ;

	public String getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}
}
