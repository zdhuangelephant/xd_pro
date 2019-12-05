package com.xiaodou.jmsgauto.constant;

import com.xiaodou.jmsgauto.util.HostHelper;
//用来做定时任务检测的静态指标
public class InitConstant {
	public static String serverName=HostHelper.getHostName();
	public static String serverQueueSetting="";	
	public static String  messageConfs="";
	public static String  rabbitConfs="";
	

	//主动监控状态 0开启 1关闭
	public static int autoState=0;
	
}
