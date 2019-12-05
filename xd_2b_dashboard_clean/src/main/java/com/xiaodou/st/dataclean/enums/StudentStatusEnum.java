package com.xiaodou.st.dataclean.enums;

public enum StudentStatusEnum {
	StudentStatus_0("0", "未注册"), StudentStatus_1("1", "注册成功"), StudentStatus_2(
			"2", "注册失败 ");
	private String code;
	private String desc;

	private StudentStatusEnum(String code, String desc) {
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
