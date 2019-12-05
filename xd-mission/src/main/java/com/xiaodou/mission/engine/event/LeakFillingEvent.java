package com.xiaodou.mission.engine.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name LeakFillingEvent 
 * @CopyRright (c) 2017 by zhaodan
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年10月9日
 * @description 查漏补缺事件
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"courseId", "majorId"})
public class LeakFillingEvent extends BaseEvent {
  /** count 答题数量 */
  @NotEmpty
  private Integer count;
  /** score 分数 */
  @NotEmpty
  private Double score;
}
