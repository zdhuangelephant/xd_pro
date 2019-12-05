package com.xiaodou.userCenter.common;

/**
 * 
 * 常量定义
 * 
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
public class Constant {

	/**
	 * 小逗通用前缀
	 */
	public static final String XIAODOU = "xiaodou:";

	public static final String COMMON_DELIMITER = ":";

	/**
	 * 用户中心
	 */
	public static final String USER_CENTER = XIAODOU + "user:center:";

	/**
	 * 短信15条校验
	 */
	public static final String SMS_CODE = USER_CENTER + "sms:";

	/**
	 * 注册验证码
	 */
	public static final String REGISTER_CHECK_CODE = USER_CENTER + "checkCode:";

	/**
	 * 反馈15次校验
	 */
	public static final String FEED_CODE = USER_CENTER + "feedcount:";

	/**
	 * 增加反馈信息
	 */
	public static final String ADD_FEED_BACK = USER_CENTER + "feedBack:";

	/**
	 * 上传凭证
	 */
	public static final String UP_TOKEN = USER_CENTER + "uptoken:";
	
	/**
	 * token登录用户
	 */
	public static final String LOGIN_TOKEN = USER_CENTER + "loginToken:";
	
	/**
	 * 登录用户
	 */
	public static final String LOGIN_INFO = USER_CENTER + "loginInfo:";
	
	/**
     * 緩存用户
     */
    public static final String CACHE_USER = USER_CENTER + "loginCacheUser:";
    
    /**
     * 重复数据
     */
    public static final String DUPLICATE_USER = USER_CENTER + "loginDuplicate:";
	
    /**LOGIN_MAIN_ACCOUNT 登录主账号*/
    public static final String LOGIN_MAIN_ACCOUNT = USER_CENTER + "loginMainAccount:";
}
