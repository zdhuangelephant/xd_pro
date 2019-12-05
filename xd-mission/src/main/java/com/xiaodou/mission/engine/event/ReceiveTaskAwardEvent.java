package com.xiaodou.mission.engine.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name ReceiveTaskAwardEvent
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月21日
 * @description 获取任务奖励事件
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"courseId", "majorId"})
public class ReceiveTaskAwardEvent extends BaseEvent {
  
  /** missionId 任务ID */
  private String missionId;
  /** recordId 任务记录ID */
  private String recordId;
  
}