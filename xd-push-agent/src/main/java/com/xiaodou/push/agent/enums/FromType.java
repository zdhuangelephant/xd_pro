package com.xiaodou.push.agent.enums;

public enum FromType {
	/** 退出登录消息 */
	login_out("0"),
	/** 赞消息*/
	praise("1"),
	/** 评论消息 */
	comment("2"),
	/** 好友消息 */
	friend("3");
	
	private String fromType;

	private FromType(String fromType) {
		this.fromType = fromType;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

}
