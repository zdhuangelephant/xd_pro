package com.xiaodou.st.dashboard.domain.score;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

@Data
@EqualsAndHashCode(callSuper = false)
public class LearnRecordDTO extends BaseValidatorPojo {
  /* 学生id */
  private Long studentId;
  /* 产品id */
  @NotEmpty
  private Long productId;
  /* 学习行为 */
  private Short learnType;
  /* 记录时间 */
  private String recordTime;

}
