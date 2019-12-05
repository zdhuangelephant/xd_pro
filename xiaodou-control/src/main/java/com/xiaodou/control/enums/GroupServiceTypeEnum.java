package com.xiaodou.control.enums;

public enum GroupServiceTypeEnum {

	NativeGroup("1", "NATIVE"), OnlineGroup("2", "ONLINE");

	GroupServiceTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getName(String index) {
		for (GroupServiceTypeEnum c : GroupServiceTypeEnum.values()) {
			if (c.getCode().equals(index)) {
				return c.name;
			}
		}
		return null;
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