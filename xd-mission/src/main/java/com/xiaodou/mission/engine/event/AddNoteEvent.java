package com.xiaodou.mission.engine.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name AddNoteEvent CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月5日
 * @description 添加笔记事件
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"courseId", "majorId"})
public class AddNoteEvent extends BaseEvent {

}
