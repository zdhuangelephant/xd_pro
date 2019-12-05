package com.xiaodou.userCenter.constant;

/**
 * @name @see com.xiaodou.userCenter.constant.UcneterModelConstant.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月7日
 * @description 用户中心模型包常量
 * @version 1.0
 */
public interface UcenterModelConstant {

  /** BLANK_NOTICE_UNSHOW 不展示系统公告和活动 */
  public static String BLANK_NOTICE_UNSHOW = "2";
  /** RELATEINFO_READED 已读通知 */
  public static String NOTICEINFO_READED = "2";
  /** RELATEINFO_UNREAD 未读通知 */
  public static String NOTICEINFO_UNREAD = "1";
  /** UNKNOWN_INFO 未知信息 */
  public static final String UNKNOWN_INFO = "待完善";
  /** MODIFY_INFO 修改信息 */
  public static final String MODIFY_INFO = "1";
  /** IMPROVE_INFO 完善资料 */
  public static final String IMPROVE_INFO = "2";

  /** PLATFORM_LOCAL 本地登录 */
  public static final String PLATFORM_LOCAL = "local";
  /** PLATFORM_QQ 三方登录 QQ登录 */
  public static final String PLATFORM_QQ = "qq";
  /** PLATFORM_WEIBO 三方登录 微博登录 */
  public static final String PLATFORM_WEIBO = "weibo";
  /** PLATFORM_WEIXIN 三方登录 微信登录 */
  public static final String PLATFORM_WEIXIN = "weixin";
  /** PLATFORM_DEVICE 三方登录 游客登录 */
  public static final String PLATFORM_DEVICE = "device";

  /* QUERY_COIN 金币相关操作 查询用户金币数 */
  public static final String QUERY_COIN = "QUERY_COIN";
  /* UPDATE_COIN 金币相关操作 更新用户金币数 */
  public static final String UPDATE_COIN = "UPDATE_COIN";
  /* COIN_DEFAULT 金币默认值 */
  public static final Integer COIN_DEFAULT = 0;

  /* 默认头像*/
  public static final String PORTRAIT =
      "http://7xigj3.com1.z0.glb.clouddn.com/85FC5E1D-2C4C-4675-AB4F-C8D3E24D4D74";

}
