package com.xiaodou.oms.service.order;

import com.xiaodou.common.util.log.LoggerUtil;


/**
 * 订单异常报警
 * @author Administrator
 *
 */
public class OrderAlerter {
	
	public static void alert(String msg){
		alert(msg, "一般");
	}

	public static void alert(String msg, String type){
		LoggerUtil.alarmInfo("[订单模块][" + type + "]" + msg);
	}
	
	public static void alertOrder(String msg){
		LoggerUtil.alarmInfo("[订单模块][一般]["+ msg+"]");
	}

}
