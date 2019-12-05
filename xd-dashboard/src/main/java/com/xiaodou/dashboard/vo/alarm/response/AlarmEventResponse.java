package com.xiaodou.dashboard.vo.alarm.response;

import com.xiaodou.dashboard.model.alarm.domain.AlarmEventDo;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.dashboard.vo.alarm.response.AlarmEventResponse.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月12日
 * @description 报警事件响应类
 * @version 1.0
 */
public class AlarmEventResponse extends ResultInfo {

  public AlarmEventResponse() {}

  public AlarmEventResponse(ResultType type) {
    super(type);
  }

  /** event 报警事件实体 */
  private AlarmEventDo event;

  public AlarmEventDo getEvent() {
    return event;
  }

  public void setEvent(AlarmEventDo event) {
    this.event = event;
  }

}
