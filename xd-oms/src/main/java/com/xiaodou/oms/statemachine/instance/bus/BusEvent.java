package com.xiaodou.oms.statemachine.instance.bus;

/**
 * 汽车票状态事件定义 (二期支付落地)
 * @author bing.cheng
 *
 */
public enum BusEvent {

	HoldSeatSuccess("HoldSeatSuccess", "占座成功"),//1
	HoldSeatFailure("HoldSeatFailure", "占座失败"),//2
	
	PayTimeout("PayTimeout", "支付超时"),//3、11
	CancelByUser("CancelByUser", "用户取消"),//4、10
	CancelByFraud("CancelByFraud","风控拒绝取消"),//5、12
	
	PaySuccess("PaySuccess", "支付成功"), //6、8、18
	PayFailure("PayFailure", "支付失败"), //7、9、19
	
	NotifyTicketFailure("NotifyTicketFailure", "通知出票失败"),//13
	HoldTicketIng("HoldTicketIng", "出票中(通知出票成功)"), //14
	HoldTicketSuccess("HoldTicketSuccess", "出票成功"), //15、16
	HoldTicketFailure("HoldTicketFailure", "出票失败"), //17、22
	
	RefundSuccess("RefundSuccess", "退款成功"),//20
	RefundFailure("RefundFailure", "退款失败");//21

	private String name;
	private String desc;

	private BusEvent(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public String getName() {
		return this.name;
	}

	public String getDesc() {
		return this.desc;
	}
}
