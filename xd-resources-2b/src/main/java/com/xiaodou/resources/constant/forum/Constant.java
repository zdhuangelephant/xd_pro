package com.xiaodou.resources.constant.forum;

/**
 * 常量类
 * 
 * @author bing.cheng
 * 
 */
public class Constant {


  /** 连接符 */
  public static final String COMMON_DELIMITER = ":";

  /** 话题 */
  public static String FORUM = "xiaodou:5th:forum";

  /** 话题分类 */
  public static String FORUM_CATEGORY = "category:list";

  /** 话题推荐 */
  public static String FORUM_RECOMMEND = "recommend:list";

  /** 话题点赞 */
  public static String FORUM_PRAISE = FORUM + "praise:";

  /** 话题评论点赞 */
  public static String FORUM_COMMENT_PRAISE = FORUM + "comment:praise:";

  /** 用户id */
  public static String FORUM_USERID = "userId:";

  /** RELATEINFO_READED 已读消息 */
  public static String RELATEINFO_READED = "2";

  /** RELATEINFO_UNREAD 未读消息 */
  public static String RELATEINFO_UNREAD = "1";

  /** YES 是 */
  public static String YES = "1";
  
  /** NO 否 */
  public static String NO = "0";
  
  /** FIRST_PAGE 首页页码 */
  public static Integer FIRST_PAGE = 1;
  /** S_FIRST_PAGE 首页页码 */
  public static String S_FIRST_PAGE = "1";
  public static Integer LIMIT_START = 0;
  public static Integer LIMIT_COUNT = 20;
}
