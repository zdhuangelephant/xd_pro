package com.xiaodou.control.enums;

public enum BaseNodeStatusEnum {

	NoApproval ("0","NoApproval"),
	Approval("1","Approval");
	BaseNodeStatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	private String code;
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}