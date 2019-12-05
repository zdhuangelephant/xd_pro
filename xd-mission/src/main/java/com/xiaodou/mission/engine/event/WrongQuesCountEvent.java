package com.xiaodou.mission.engine.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name @see com.xiaodou.mission.engine.event.CollectionEvent.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月17日
 * @description 错题数量事件
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"courseId", "majorId"})
public class WrongQuesCountEvent extends BaseEvent {
  protected WrongQuesCountEvent() {}

  /** count 错题总数量 */
  @NotEmpty
  private Integer count;

}
