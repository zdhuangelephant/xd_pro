package com.xiaodou.mission.engine.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name DailyPracticeEvent CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月16日
 * @description 每日一练事件
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"courseId", "majorId"})
public class DailyPracticeEvent extends BaseEvent {

  /** count 答题数量 */
  @NotEmpty
  private Integer count;
  /** score 分数 */
  @NotEmpty
  private Double score;
  /** lCurrentTime 当前时间 */
  private Long lCurrentTime;
}
