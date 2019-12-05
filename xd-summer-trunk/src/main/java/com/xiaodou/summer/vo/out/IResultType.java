package com.xiaodou.summer.vo.out;

/**
 * 返回值类型拓展点
 * 方便业务系统对返回码做拓展操作
 * @author dan.zhao
 */
interface IResultType {

	/**
	 * 获取返回码值
	 * @return
	 */
	public String getCode();
	
	/**
	 * 获取码值描述
	 * @return
	 */
	public String getMsg();
	
	/**
	 * 获取处理服务器IP
	 * @return
	 */
	public String getServerIp();
	
}
