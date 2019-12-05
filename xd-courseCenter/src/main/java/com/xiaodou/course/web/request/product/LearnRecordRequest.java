package com.xiaodou.course.web.request.product;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * Created by zyp on 15/8/9.
 */
public class LearnRecordRequest extends BaseRequest {

  // 学习时间
  @NotEmpty
  private Integer time;

  public Integer getTime() {
    return time;
  }

  public void setTime(Integer time) {
    this.time = time;
  }
}
