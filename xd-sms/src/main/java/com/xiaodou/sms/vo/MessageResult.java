package com.xiaodou.sms.vo;

public class MessageResult {
	/**
	 * 发送状态 0：成功  -1：失败
	 */
	private String status;

	/**
	 * 失败原因
	 */
	private String failReason;

	public String isStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	} 

}
