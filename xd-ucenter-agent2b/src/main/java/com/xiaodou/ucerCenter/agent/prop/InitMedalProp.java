package com.xiaodou.ucerCenter.agent.prop;

import com.xiaodou.common.util.FileUtil;


/**
 * 勋章默认值配置文件
 * @author lidehong
 */
public class InitMedalProp {
	
	/**
	 * 配置文件
	 */
	private static FileUtil prop = FileUtil.getInstance("/conf/custom/env/medal.properties");
	
	/**
	 * @return 获取配置文件信息
	 */
	private static FileUtil getProperty(){
		if(prop == null)
			synchronized (InitMedalProp.class) {
				if(prop == null)
					prop = FileUtil.getInstance("/conf/custom/env/medal.properties");
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
