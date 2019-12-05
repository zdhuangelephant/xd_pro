package com.xiaodou.async.constant;

public class AsyncMessageConst {

  /** DOMAIN_DEALRESULT_INIT 初始化处理结果 */
  public static final String DOMAIN_DEALRESULT_INIT = "-1";
  /** DOMAIN_DEALRESULT_AGREE 处理结果 同意 */
  public static final String DOMAIN_DEALRESULT_AGREE = "0";
  /** DOMAIN_DEALRESULT_UNAGREE 处理结果 拒绝 */
  public static final String DOMAIN_DEALRESULT_UNAGREE = "1";
  /** DOMAIN_DEALRESULT_UNAGREE 处理结果 静止状态 */
  public static final String DOMAIN_DEALRESULT_STATIC = "2";
  /** SYSTEM_MES 系统消息 */
  public static final short POJO_TYPE_SYSTEMMES = 1;
  /** OTHER_MES 其它消息 */
  public static final short POJO_TYPE_OTHERMES = 2;
  /** POJO_STYPE_SYSTEMMES 系统消息 */
  public static final String POJO_STYPE_SYSTEMMES = "1";
  /** POJO_STYPE_OTHERMES 其它消息 */
  public static final String POJO_STYPE_OTHERMES = "2";
  /** UNREAD 未读 */
  public static final short POJO_STATUS_UNREAD = 1;
  /** READED 已读 */
  public static final short POJO_STATUS_READED = 2;
  /** CLASSIFY_ADDFRIEND 加好友 */
  public static final short POJO_CLASSIFY_ADDFRIEND = 1;
  /** CLASSIFY_ADDFRIEND 加好友回调 */
  public static final short POJO_CLASSIFY_ADDFRIEND_CALLBACK = 11;
  /** CLASSIFY_PK 发起PK */
  public static final short POJO_CLASSIFY_PK = 2;
  /** POJO_CLASSIFY_PK_CALLBACK 发起PK回调 */
  public static final short POJO_CLASSIFY_PK_CALLBACK = 12;
  /** CLASSIFY_PRAISE 点赞 */
  public static final short POJO_CLASSIFY_PRAISE = 3;
  /** CLASSIFY_SYSNOTICE 系统通知 暂时取消 */
  // public static final short POJO_CLASSIFY_SYSNOTICE = 4;
  /** CLASSIFY_SYSMES 系统消息 */
  public static final short POJO_CLASSIFY_SYSMES = 5;

}
