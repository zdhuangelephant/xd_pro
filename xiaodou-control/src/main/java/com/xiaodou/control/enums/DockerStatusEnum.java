package com.xiaodou.control.enums;

public enum DockerStatusEnum {

	NoDocker("0","NoDocker"),
	DockerRunning("1","DockerRunning"),
	DockerStop("2","DockerStop"),
	UnknownDocker("3","UnknownDocker");
	DockerStatusEnum(String code, String name) {
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