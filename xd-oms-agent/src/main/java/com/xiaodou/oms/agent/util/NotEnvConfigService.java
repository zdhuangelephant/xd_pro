package com.xiaodou.oms.agent.util;

import com.xiaodou.common.util.FileUtil;

public class NotEnvConfigService {
	/**
	 * 配置文件
	 */
	private static FileUtil confFile = FileUtil.getInstance(
			"/conf/custom/notenv/omsnenv.properties");

	/**
	 * @return 获取配置文件信息
	 */
	private static FileUtil getProperty() {
		if (confFile == null)
			synchronized (OrderConfigService.class) {
				if (confFile == null)
					confFile =  FileUtil.getInstance("/conf/custom/notenv/omsnenv.properties");
			}
		return confFile;
	}

	/**
	 * 获取参数值
	 * 
	 * @param code
	 *            参数key
	 * @return 参数值
	 */
	public static String getParams(String code) {
		return getProperty().getProperties(code);
	}
}
