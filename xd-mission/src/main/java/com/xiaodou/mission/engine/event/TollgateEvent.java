package com.xiaodou.mission.engine.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name TollgateEvent CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月16日
 * @description 闯关答题事件
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"courseId", "majorId"})
public class TollgateEvent extends BaseEvent {
  protected TollgateEvent() {}

  /** chapterId 所属章ID */
  private String chapterId;
  /** tollgateId 关卡ID */
  private String tollgateId;
  /** count 答题数量 */
  @NotEmpty
  private Integer count;
  /** starLevel 评定星级 */
  @NotEmpty
  private Integer starLevel = 0;
  /** score 关卡得分 */
  @NotEmpty
  private Double score = 0d;
  /** credit 关卡获取积分 */
  @NotEmpty
  private Integer credit = 0;

  public String getTollgateId() {
    if (StringUtils.isNotBlank(tollgateId))
      return tollgateId;
    else if (StringUtils.isNotBlank(chapterId))
      return chapterId;
    else
      return getCourseId();
  }

  @Override
  public String getThreshold() {
    return getTollgateId();
  }
}
