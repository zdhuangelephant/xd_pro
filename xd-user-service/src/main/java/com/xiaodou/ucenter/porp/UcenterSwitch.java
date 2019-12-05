package com.xiaodou.ucenter.porp;

import com.xiaodou.common.util.FileUtil;


/**
 * 七牛配置文件
 * @author dan.zhao
 */
public class UcenterSwitch {
	
	/**
	 * 配置文件
	 */
	private static FileUtil prop = FileUtil.getInstance("/conf/custom/env/ucenter_switch.properties");
	
	/**
	 * @return 获取配置文件信息
	 */
	private static FileUtil getProperty(){
		if(prop == null)
			synchronized (UcenterSwitch.class) {
				if(prop == null)
					prop = FileUtil.getInstance("/conf/custom/env/ucenter_switch.properties");
			}
		return prop;
	}

	/**
	 * 获取参数值
	 * @param code 参数key
	 * @return 参数值
	 */
	public static String getParams(String code) {
		return getProperty().getProperties(code);
	}
	
	public static boolean getSwitch(String code) {
	  return getParams(code).equals("ON");
	}
	
	private static final String CHECK_DEVICE_ID = "xd.ucenter.checkSwitch";
	public static boolean checkDeviceId(){
	  return getSwitch(CHECK_DEVICE_ID);
	}

}
