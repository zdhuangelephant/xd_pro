package com.xiaodou.forum.constant;

/**
 * 
 * 话题常量
 * 
 * <p>
 * 修改历史: <br>
 * 修改日期 修改人员 版本 修改内容<br>
 * -------------------------------------------------<br>
 * 2015年4月12日 下午10:03:57 weichao.zhai 1.0 初始化创建<br>
 * </p>
 * 
 * @author weichao.zhai
 * @version 1.0
 */
public interface ForumConstant {
  /**
   * 置顶标识
   */
  public static int TopForum = 1;

  /**
   * 不置顶标识
   */
  public static int NotTopForum = 0;

  /**
   * 热门评论标识
   */
  public static int HotComment = 1;

  /**
   * 普通评论标识
   */
  public static int NothotComment = 0;

  /**
   * 查询置顶话题的数量
   */
  public static int TopSize = 3;

  /** COMMENT_MES 评论消息类型 */
  public static Short COMMENT_MES = 1;

  /** PRAISE_MES 点赞消息类型 */
  public static Short PRAISE_MES = 2;

  /** STATUS_INIT 状态-初始化 */
  public static Short STATUS_INIT = 1;

  /** STATUS_READED 状态-已读 */
  public static Short STATUS_READED = 2;
}
