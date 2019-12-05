package com.xiaodou.mission.vo.event;

import java.sql.Timestamp;

import lombok.Data;

import org.springframework.util.Assert;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.dataclean.core.event.DCCoreEvent;
import com.xiaodou.mission.vo.event.FinishAllMissionEvent.FinishAllMessionRequest;

/**
 * @name FinishAllMissionMessage
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月24日
 * @description 完成全部任务异步消息参数类
 * @version 1.0
 */
public class FinishAllMissionEvent extends DCCoreEvent<FinishAllMessionRequest> {

  /** EVENT_NAME 完成全部任务事件名 */
  private static final String EVENT_NAME = "finishAllMissionEvent";

  public FinishAllMissionEvent(FinishAllMessionRequest message) {
    setEventName(EVENT_NAME);
    Assert.isTrue(message != null && StringUtils.isNotBlank(message.getModule()),
        "message model and module can't be null.");
    setModule(message.getModule());
    setDataModel(message);
  }
  
  /**
   * @name @see com.xiaodou.mission.vo.message.FinishAllMessionRequest.java
   * @CopyRright (c) 2017 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年3月6日
   * @description 完成课程全部任务事件
   * @version 1.0
   */
  @Data
  public static class FinishAllMessionRequest {
    /** userId 用户ID */
    private String userId;
    /** courseId 课程ID */
    private String courseId;
    /** majorId 专业ID */
    private String majorId;
    /** module 所属模块 */
    private String module;
    /** currentTime 当前日期 */
    private Timestamp currentTime;
  }

}
