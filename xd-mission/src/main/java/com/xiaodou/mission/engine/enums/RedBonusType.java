package com.xiaodou.mission.engine.enums;

/**
 * @name @see com.xiaodou.mission.engine.enums.RedBounsType.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月18日
 * @description 红包奖励类型
 * @version 1.0
 */
public enum RedBonusType {

  /** Null 没有红包奖励 */
  Null,
  /** tollgate_item 发起节闯关 */
  Tollgate_item,
  /** tollgate_chapter 发起章闯关 */
  Tollgate_chapter,
  /** tollgate_course 发起课程闯关 */
  Tollgate_course,
  /** tollgate_complete_item 完成节闯关 */
  Tollgate_complete_item,
  /** tollgate_complete_chapter 完成章闯关 */
  Tollgate_complete_chapter,
  /** tollgate_complete_course 完成课程闯关 */
  Tollgate_complete_course,
  /** random_pk 发起随机pk */
  Random_pk,
  /** random_pk_win 赢得随机pk */
  Random_pk_win,
  /** friend_pk 发起好友pk */
  Friend_pk,
  /** friend_pk_win 赢得好友pk */
  Friend_pk_win,
  /** Leak_filling_complete 完成查漏补缺  */
  Leak_filling_complete
}
