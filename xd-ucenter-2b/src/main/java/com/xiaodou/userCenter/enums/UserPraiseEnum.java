package com.xiaodou.userCenter.enums;

public enum UserPraiseEnum {
	isNotPraised("0", "没有点赞"), isPraised("1", "已经点赞");

	private String code;
	private String desc;

	private UserPraiseEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
