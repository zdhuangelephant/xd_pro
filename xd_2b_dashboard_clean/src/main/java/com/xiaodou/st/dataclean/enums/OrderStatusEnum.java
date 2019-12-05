package com.xiaodou.st.dataclean.enums;

public enum OrderStatusEnum {
	OrderStatus_0("0", "待缴费"), OrderStatus_1("1", "已缴费"), OrderStatus_2("2",
			"未缴费");
	private String code;
	private String desc;

	private OrderStatusEnum(String code, String desc) {
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
