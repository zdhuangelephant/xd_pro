package com.xiaodou.control.enums;

public enum CommandEnum {

	Deploy("1", "自动部署", "自动部署"),
	ReStart("2", "重启", "重启");
	CommandEnum(String code, String name, String desc) {
		this.code = code;
		this.name = name;
		this.desc = desc;
	}

	private String code;
	private String name;
	private String desc;

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}