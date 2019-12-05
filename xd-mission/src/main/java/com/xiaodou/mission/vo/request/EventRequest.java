package com.xiaodou.mission.vo.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

public class EventRequest extends BaseValidatorPojo {

  /** eventTag 事件唯一tag */
  @NotEmpty
  private String eventTag;
  /** eventType 事件类型 */
  @NotEmpty
  private String eventType;
  /** eventMessage 事件消息体 */
  @NotEmpty
  private String eventMessage;

  public String getEventTag() {
    return eventTag;
  }

  public void setEventTag(String eventTag) {
    this.eventTag = eventTag;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public String getEventMessage() {
    return eventMessage;
  }

  public void setEventMessage(String eventMessage) {
    this.eventMessage = eventMessage;
  }

}
