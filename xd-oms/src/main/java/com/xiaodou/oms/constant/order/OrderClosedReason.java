package com.xiaodou.oms.constant.order;

/**
 * 
 * @Title:OrderClosedReason.java
 * 
 * @Description:定义订单关闭原因
 *
 * @author  zhaoyang
 * @date    Mar 13, 2014 8:17:56 AM
 * @version V1.0
 */
public enum OrderClosedReason {
	
	ByUser("ByUser","用户取消"),
	ByCustomerService("ByCustomerService","客服取消"),
	PayTimeout("PayTimeout","支付超时"),
	DeliveringFailure("DeliveringFailure","下单失败"),
	DeliveringTimeout("DeliveringTimeout","下单超时"),
	DeliveredFailure("DeliveredFailure","派单失败"),
	DeliveredTimeout("DeliveredTimeout","派单超时");
	
	private String name;
	private String desc;

	private OrderClosedReason(String name, String desc) {
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
