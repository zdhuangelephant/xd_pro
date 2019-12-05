package com.xiaodou.resources.request.product;

import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 用户访问课程权限request
 * 
 * @author bing.cheng
 *
 */
public class ProductAuthRequest extends BaseRequest {

  // itemId
  @NotEmpty
  private Integer itemId;

  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }
}
