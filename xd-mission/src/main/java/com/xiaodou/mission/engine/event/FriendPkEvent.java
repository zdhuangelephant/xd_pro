package com.xiaodou.mission.engine.event;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @name @see com.xiaodou.mission.engine.event.PkEvent.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月17日
 * @description 好友pk事件
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class FriendPkEvent extends BaseEvent {
  protected FriendPkEvent() {}

  /** isWinner 是否胜者 */
  private Boolean isWinner = Boolean.FALSE;
  /** isFailer 是否败者 */
  private Boolean isFailer = Boolean.FALSE;
  /** count 答题数量 */
  private Integer count;
  /** score 分数 */
  private Double score;
  /** credit 关卡获取积分 */
  private Integer credit;

}
