package com.xiaodou.mission.engine.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.mission.engine.event.CreditTotalEvent.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月12日
 * @description 总积分时间
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class CreditTotalEvent extends BaseEvent {
  protected CreditTotalEvent() {}

  /** totalCredit 总积分 */
  @NotEmpty
  private Integer totalCredit;

}
