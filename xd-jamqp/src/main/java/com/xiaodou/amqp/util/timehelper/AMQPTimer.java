package com.xiaodou.amqp.util.timehelper;

import java.util.Date;

/**
 * 获取时间
 * @author heshixiong
 *
 */
public class AMQPTimer {
	/**
	 * 获取当前时间,单位毫秒
	 * @return 返回当前时间
	 */
	public static long getNowTime(){
		long now = new Date().getTime();
		return now;
	}
	public static long getCurrentTime(){
		return System.currentTimeMillis();
	}
}
