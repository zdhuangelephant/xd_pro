package com.xiaodou.mission.engine.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name @see com.xiaodou.mission.engine.event.ImproveInfoEvent.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月17日
 * @description 完善信息事件
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ImproveInfoEvent extends BaseEvent {
  protected ImproveInfoEvent() {}

  /** improveProtrait 更新了头像 */
  private Boolean improveProtrait = Boolean.FALSE;

  /** improveSign 更新了签名 */
  private Boolean improveSign = Boolean.FALSE;

  /** improveGender 更新了昵称 */
  private Boolean improveNickname = Boolean.FALSE;

}
