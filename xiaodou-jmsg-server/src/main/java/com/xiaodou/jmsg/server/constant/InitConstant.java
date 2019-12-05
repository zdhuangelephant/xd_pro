package com.xiaodou.jmsg.server.constant;

import com.xiaodou.jmsg.server.util.HostHelper;
//用来做定时任务检测的静态指标
public class InitConstant {
	public static String serverName=HostHelper.getHostName();
	public static String serverQueueSetting="";	
	public static String  messageConfs="";
	public static String  rabbitConfs="";
	
	
	//超时重新执行时间
	public static long overTime=3600000;
	//URL短时间内最大失败数(此时间大于等于上述时间间隔)
	public static long maxFailCount=5;
	
	//主动监控状态
	public static int autoState=0;
	
}
