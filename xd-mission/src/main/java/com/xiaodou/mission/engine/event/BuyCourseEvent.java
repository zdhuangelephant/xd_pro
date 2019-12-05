package com.xiaodou.mission.engine.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name @see com.xiaodou.mission.engine.event.BuyCourseEvent.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月16日
 * @description 购买课程事件
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AddValidField(annotiation = AnnotationType.NotEmpty, field = "courseId")
public class BuyCourseEvent extends BaseEvent {
}
