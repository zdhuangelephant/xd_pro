package com.xiaodou.facerecognitiondemo.prop;

import com.xiaodou.common.util.FileUtil;


/**
 * 七牛配置文件
 * @author dan.zhao
 */
public class SevenCowProp {
	
	/**
	 * 配置文件
	 */
	private static FileUtil prop = FileUtil.getInstance("/conf/custom/env/seven_cow_info.properties");
	
	/**
	 * @return 获取配置文件信息
	 */
	private static FileUtil getProperty(){
		if(prop == null)
			synchronized (SevenCowProp.class) {
				if(prop == null)
					prop = FileUtil.getInstance("/conf/custom/env/seven_cow_info.properties");
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

}
