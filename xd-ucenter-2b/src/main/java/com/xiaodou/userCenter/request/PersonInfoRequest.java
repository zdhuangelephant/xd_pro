package com.xiaodou.userCenter.request;


public class PersonInfoRequest extends BaseRequest{

	/**
	 * 目标用户ID //非必传 为空则返回当前用户信息
	 */
	private String targetUserId;

	public PersonInfoRequest(){}
	
	public String getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}
	
}
