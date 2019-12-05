package com.xiaodou.oms.constant.order;

/**
 * 
 * @Title:OrderClosedReason.java
 * 
 * @Description:定义order_desc字段的含义
 *
 * @author  zhaoyang
 * @date    Mar 13, 2014 8:17:56 AM
 * @version V1.0
 */
public enum OrderStatusDesc {
	
	Gathering("Gathering","收款"),
	Refund("Refund","退款");
	
	
	private String name;
	private String desc;

	private OrderStatusDesc(String name, String desc) {
		this.name = name;
		this.desc = desc;
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
