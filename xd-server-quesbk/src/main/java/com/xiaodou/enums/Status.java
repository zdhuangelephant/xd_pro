package com.xiaodou.enums;

public enum Status {
	NOMAL("1", "正常"), UNUSED("2", "废弃");
	private Status(String code, String name) {
		_code = code;
		_name = name;
	}

	private String _code;
	private String _name;

	public String getCode() {
		return _code;
	}

	public void setCode(String code) {
		this._code = code;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}
	
}
