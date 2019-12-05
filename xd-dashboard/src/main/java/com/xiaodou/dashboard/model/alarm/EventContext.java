package com.xiaodou.dashboard.model.alarm;

import java.util.List;

import com.xiaodou.dashboard.model.alarm.domain.AlarmEventDo;

/**
 * @name @see com.xiaodou.dashboard.engine.EventContext.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年11月25日
 * @description 报警事件上下文组
 * @version 1.0
 */
public class EventContext {

  /** module 所属模块 */
  private String module;

  /** events 模块事件组 */
  private List<AlarmEventDo> events;

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public List<AlarmEventDo> getEvents() {
    return events;
  }

  public void setEvents(List<AlarmEventDo> events) {
    this.events = events;
  }

}
