package com.xiaodou.ms.prop;

import com.xiaodou.common.util.FileUtil;

public class UserTagProp {
	/**
	 * 配置文件
	 */
	private static FileUtil tagFile = FileUtil.getInstance("/conf/custom/env/user_tag.properties");
	/**
	 * @return 获取配置文件信息
	 */
	private static FileUtil getProperty(){
		if(tagFile==null)
			synchronized (UserTagProp.class) {
				tagFile = FileUtil.getInstance("/conf/custom/env/user_tag.properties");
			}
		return tagFile;
	}
	/**
	 * 获取参数值
	 * @param code 参数key
	 * @return 参数值
	 */
	public static String getParams(String code){
		return getProperty().getProperties(code);
	}
}
