package com.xiaodou.control.enums;

public enum MiddleServerEnum {

	Mysql("5001","Mysql"),
	Redis("5002","Redis"),
	Mongo("5003","Mongo"),
	Flume("5004","Flume"),
	RabbitMq("5005","RabbitMq"),
	Zookeeper("5006","Zookeeper"),
	Jstorm("5007","Jstorm");
	MiddleServerEnum(String code, String name) {
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
	public static String getName(String index) {
		for (MiddleServerEnum c : MiddleServerEnum.values()) {
			if (c.getCode().equals(index)) {
				return c.name;
			}
		}
		return null;
	}
}