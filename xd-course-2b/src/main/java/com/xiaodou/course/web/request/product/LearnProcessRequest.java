package com.xiaodou.course.web.request.product;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * Created by zyp on 15/8/9.
 */
public class LearnProcessRequest extends BaseRequest {

  // itemId
  @NotEmpty
  private Long itemId;

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }
}
