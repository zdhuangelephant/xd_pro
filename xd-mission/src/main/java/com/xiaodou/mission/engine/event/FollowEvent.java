package com.xiaodou.mission.engine.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name @see com.xiaodou.mission.engine.event.FollowEvent.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月17日
 * @description 关注事件
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"targetUserId"})
public class FollowEvent extends BaseEvent {
  protected FollowEvent() {}
}
