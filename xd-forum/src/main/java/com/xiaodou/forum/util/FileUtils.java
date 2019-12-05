package com.xiaodou.forum.util;

import com.xiaodou.common.util.FileUtil;

/**
 * 读取配置文件
 * 
 * @author bing.cheng
 *
 */
public class FileUtils {
	/**不需要登录，也能访问的url*/
	public static FileUtil NO_LOGIN_ACCESS_FILTER_URL = FileUtil.getInstance(
			"/conf/custom/env/access_url.properties");
	/**用户中心相关参数*/
	public static FileUtil USER_CENTER_PROPERTIES = FileUtil.getInstance(
			"/conf/custom/env/user_center.properties");
	/**话题参数*/
	public static FileUtil FORUM_PROP = FileUtil.getInstance(
			"/conf/custom/env/forum.properties");

}
