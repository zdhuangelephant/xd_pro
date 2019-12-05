package com.xiaodou.oms.service.order;

import java.util.HashMap;
import java.util.Map;

public class OrderStatusAndStateClassMap {
	
	public Map<Integer, String> getOrderStatusAndStateClassMap() {
		return orderStatusAndStateClassMap;
	}

	public void setOrderStatusAndStateClassMap(
			Map<Integer, String> orderStatusAndStateClassMap) {
		this.orderStatusAndStateClassMap = orderStatusAndStateClassMap;
	}

	private Map<Integer, String> orderStatusAndStateClassMap = new HashMap<Integer, String>();

}
